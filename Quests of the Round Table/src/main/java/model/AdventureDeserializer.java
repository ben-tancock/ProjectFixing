package model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdventureDeserializer extends JsonDeserializer<Adventure>{

	@Override
	public Adventure deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		if(node.has("ally")) {
			return mapper.treeToValue(node, Ally.class);
		} else if(node.has("amour")) {
			return mapper.treeToValue(node, Amour.class);
		} else if(node.has("foe")){
			return mapper.treeToValue(node, Foe.class);
		} else if(node.has("test")){
			return mapper.treeToValue(node, Test.class);
		} else {
			return mapper.treeToValue(node, Weapon.class);
		}
	}

}
