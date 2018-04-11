package util;

public class ServerSubscribeEndpoints {
	public static final String USERS = "/users";
	public static final String REGISTER = USERS + "/register";
	public static final String START_GAME = USERS + "/startGame-";
	public static final String STORY_DRAW = USERS + "/storyDraw";
	public static final String PLAYED_CARD = USERS + "/playedCard";
	public static final String DISCARDED_CARD = USERS + "/discardedCard";
}
