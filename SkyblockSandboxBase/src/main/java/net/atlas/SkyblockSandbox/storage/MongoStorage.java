package net.atlas.SkyblockSandbox.storage;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import net.atlas.SkyblockSandbox.database.mongo.MongoDB;
import net.atlas.SkyblockSandbox.files.DatabaseInformationFile;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MongoStorage implements MongoDB
{
	private static boolean connected = false;

	private static MongoCollection<Document> col;

	public MongoStorage() {

	}


	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public void connect() {
		DatabaseInformationFile dbinfo = new DatabaseInformationFile();

		MongoClient client;

		if (dbinfo.getConfiguration().getBoolean("use"))
			client = MongoClients.create(dbinfo.getConfiguration().getString("uri"));
		else
			client = MongoClients.create("mongodb+srv://admin:anyg0nSdrBvN0ltL@atlasdb.rpbqq.mongodb.net/test");

		MongoDatabase database = client.getDatabase("AtlasSandbox");
		col = database.getCollection("storage_data");
		System.out.println(col);
		Bukkit.getLogger().info("Successfully connected to MongoDB!");
		connected = true;
	}

	@Override
	public void setData(UUID uuid, String key, Object value) {
		Document query = new Document("uuid", uuid.toString());
		Document found = col.find(query).first();

		if (found == null) {
			Document update = new Document("uuid", uuid.toString());
			update.append(key, value);

			col.insertOne(update);
			return;
		}

		col.updateOne(Filters.eq("uuid", uuid.toString()), Updates.set(key, value));
	}

	@Override
	public Object getData(UUID uuid, String key) {

		Document query = new Document("uuid", uuid.toString());
		if (query.isEmpty()) {
			setData(uuid, key, 0D);
		} else {
			if (col.find(query).first() != null) {
				Document found = col.find(query).first();
				if (found.get(key) != null) {
					return found.get(key);
				} else {
					setData(uuid, key, 0D);
					Document found2 = col.find(query).first();
					return found2.get(key);
				}

			} else {
				setData(uuid, key, 0D);
				if (col.find(query).first() != null) {
					Document found2 = col.find(query).first();
					return found2.get(key);
				}
			}

		}
		return null;
	}

	@Override
	public boolean remove(UUID uuid) {

		Document query = new Document("uuid", uuid.toString());
		Document found = col.find(query).first();

		if (found == null) return false;

		col.deleteOne(found);
		return true;
	}

	@Override
	public List<Document> getAllDocuments() {
		FindIterable<Document> docs = col.find();
		MongoCursor<Document> cursor = docs.iterator();

		List<Document> found = new ArrayList<>();
		try {
			while (cursor.hasNext()) {
				found.add(cursor.next());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return found;
	}
}
