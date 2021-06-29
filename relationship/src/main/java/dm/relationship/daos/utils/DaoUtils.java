package dm.relationship.daos.utils;

import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * Created by lee on 7/13/17.
 */
public class DaoUtils {
    private static final Logger logger = LoggerFactory.getLogger(DaoUtils.class);

    public static int findDistinctFieldAndCondition(MongoCollection collection, String field, Document document) {
        DistinctIterable<String> result = collection.distinct(field, document, String.class);
        Iterator<String> it = result.iterator();
        int count = 0;
        while (it.hasNext()) {
            it.next();
            count += 1;
        }
        return count;
    }
}
