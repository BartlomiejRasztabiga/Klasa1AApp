import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Students {

    public List<Student> getAllStudents() throws Exception {
        String response = NetworkUtilities.getStudents("tcknac");
        if (response != null) {
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Student>>() {}.getType();
            List<Student> studentsList = gson.fromJson(response, collectionType);
            return studentsList;
        }

        return null;

    }
}
