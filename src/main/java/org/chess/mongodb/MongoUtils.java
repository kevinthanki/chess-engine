package org.chess.mongodb;

import java.util.Objects;

import org.bson.Document;
import org.chess.api.util.Constants;
import org.chess.exception.GenericException;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoUtils {

	private MongoUtils() {
	}

	private static MongoClient client;

	public static MongoClient getClient() {
		if (Objects.isNull(client)) {
			client = MongoClients.create("mongodb://localhost:27017");
		}
		return client;
	}

	public static MongoCollection<Document> getCollection(String collectionName) {
		MongoDatabase database = client.getDatabase(Constants.DB_NAME);

		if (Objects.isNull(database)) {
			throw new GenericException("Database does not exist");
		}

		return database.getCollection(collectionName);
	}
	
//	public static Object getValue(String collectionName, String key) {
////		getCollection(collectionName).
//	}
	
	

}
