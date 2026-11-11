const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "alice_123";
const BOB_UID = "bob_456";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

// --- TEAMS COLLECTION TESTS ---

test("Unauthenticated user: cannot read teams", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("teams").get());
});

test("Authenticated user: can create their own team", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const now = new Date();
  await assertSucceeds(
    aliceDb.collection("teams").doc("team_1").set({
      userId: ALICE_UID,
      authorName: "Alice",
      name: "Thunder Stunner Squad",
      tag: "PvE",
      description: "Fast lightning burst team",
      memberIds: ["001", "002"],
      membersJson: "[]",
      isPublic: true,
      upvotes: 0,
      createdAt: now,
      updatedAt: now,
    })
  );
});

test("Authenticated user: cannot create team with someone else's userId", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const now = new Date();
  await assertFails(
    aliceDb.collection("teams").doc("team_2").set({
      userId: BOB_UID,
      name: "Spoofed Team",
      tag: "PvP",
      memberIds: [],
      membersJson: "[]",
      isPublic: true,
      upvotes: 0,
      createdAt: now,
      updatedAt: now,
    })
  );
});

test("Authenticated user: can read public teams and their own private teams", async () => {
  await testEnv.withSecurityRulesDisabled(async (context) => {
    const db = context.firestore();
    const now = new Date();
    await db.collection("teams").doc("pub_team").set({
      userId: BOB_UID,
      name: "Bob Public",
      tag: "PvP",
      memberIds: [],
      membersJson: "[]",
      isPublic: true,
      createdAt: now,
      updatedAt: now,
    });
    await db.collection("teams").doc("priv_team").set({
      userId: BOB_UID,
      name: "Bob Private",
      tag: "PvP",
      memberIds: [],
      membersJson: "[]",
      isPublic: false,
      createdAt: now,
      updatedAt: now,
    });
  });

  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertSucceeds(aliceDb.collection("teams").doc("pub_team").get());
  await assertFails(aliceDb.collection("teams").doc("priv_team").get());
});

// --- DISCUSSION POSTS TESTS ---

test("Authenticated user: can create discussion post", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const now = new Date();
  await assertSucceeds(
    aliceDb.collection("posts").doc("post_1").set({
      authorUid: ALICE_UID,
      authorName: "Alice",
      aniimoId: "001",
      aniimoName: "Ignisaur",
      title: "Best Awakening Build for Ignisaur",
      body: "Invest 30 in ATK and 20 in Break for huge burst.",
      tag: "Builds",
      upvotes: 0,
      commentsCount: 0,
      createdAt: now,
      updatedAt: now,
    })
  );
});

test("Non-author cannot delete discussion post", async () => {
  await testEnv.withSecurityRulesDisabled(async (context) => {
    const now = new Date();
    await context.firestore().collection("posts").doc("post_bob").set({
      authorUid: BOB_UID,
      authorName: "Bob",
      title: "Bob post",
      body: "Content",
      tag: "General",
      upvotes: 0,
      commentsCount: 0,
      createdAt: now,
      updatedAt: now,
    });
  });

  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertFails(aliceDb.collection("posts").doc("post_bob").delete());
});

// --- LEADERBOARD & LEGIT VOTE TESTS ---

test("Authenticated user: can submit leaderboard entry with video link", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const now = new Date();
  await assertSucceeds(
    aliceDb.collection("leaderboard_entries").doc("entry_1").set({
      submitterUid: ALICE_UID,
      runnerName: "SpeedyAlice",
      dungeonName: "Sunken Citadel",
      teamName: "Frost Burst Team",
      aniimoIds: ["001", "004", "007", "010"],
      clearTimeSeconds: 84,
      clearTimeFormatted: "01:24.00",
      videoUrl: "https://youtu.be/sample123",
      notes: "Sub 1:25 achieved with break timing",
      legitVotes: 0,
      notLegitVotes: 0,
      isVerified: false,
      createdAt: now,
      updatedAt: now,
    })
  );
});

test("Authenticated user: can cast a Legit verification vote on an entry", async () => {
  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  const now = new Date();
  await assertSucceeds(
    bobDb.collection("leaderboard_entries").doc("entry_1").collection("votes").doc(BOB_UID).set({
      voterUid: BOB_UID,
      isLegit: true,
      reason: "Verified clean rotation and timer in video",
      createdAt: now,
    })
  );
});

// --- PATCH NOTES TESTS ---

test("Authenticated user: can publish synced patch notes", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const now = new Date();
  await assertSucceeds(
    aliceDb.collection("patches").doc("v1_2_0").set({
      version: "1.2.0",
      title: "Whisperwake Isles Expansion & Balance",
      releaseDate: "2026-11-04",
      sourceUrl: "https://aniimo.game/patch-1-2-0",
      summary: "Added 12 new Whisperwake forms and rebalanced Flame Burst multiplier",
      skillChangesJson: "[]",
      statChangesJson: "[]",
      newAniimoJson: "[]",
      syncedByUid: ALICE_UID,
      createdAt: now,
    })
  );
});
