package util;

public class ServerSubscribeEndpoints {
	public static final String USERS = "/users";
	public static final String REGISTER = USERS + "/register";
	public static final String START_GAME = USERS + "/startGame-";
	public static final String STORY_DRAW = USERS + "/storyDraw";
	public static final String PLAYED_CARD = USERS + "/playedCard-";
	public static final String DISCARDED_CARD = USERS + "/discardedCard-";
	public static final String PARTICIPANT_ASK = USERS + "askParticipant-";
	public static final String OVERFLOW = USERS + "/overflow-";
	public static final String QUEST_START = USERS + "/questStart";
	public static final String SPONSOR_ASK = USERS + "/sponsorAsk-";
	public static final String SPONSORING_START = USERS + "/sponsoringStart-";
	public static final String SPONSORING_END = USERS + "/sponsoringEnd-";
	public static final String QUEST_END = USERS + "/questEnd";
	public static final String TOURNAMENT_START = USERS + "/tournamentStart";
	public static final String TOURNAMENT_END = USERS + "/tournamentEnd";
	public static final String EVENT_START = USERS + "/eventStart";
	public static final String EVENT_END = USERS + "/eventEnd";
}
