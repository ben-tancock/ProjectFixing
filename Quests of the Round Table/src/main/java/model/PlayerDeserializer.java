package model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlayerDeserializer extends JsonDeserializer<Player>{

	@Override
	public Player deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		if(node.has("person")) {
			return mapper.treeToValue(node, Person.class);
		} else if(node.has("AI1")) {
			return mapper.treeToValue(node, ArtificialIntelligence1.class);
		} else {
			return mapper.treeToValue(node, ArtificialIntelligence2.class);
		}
	}

}
