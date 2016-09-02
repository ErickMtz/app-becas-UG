package com.jamer.jaimeloyola.becas.Registro;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaime Loyola on 23/8/2016.
 *
 * Clase que interpreta el flujo de datos con formato JSON
 * y retorna en una lista de objeto tipo User.
 */
public class JsonUserParser {

    public List<User> leerFlujoJson(InputStream in) throws IOException {
        // Nueva instancia de JsonReader
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            //Leer texto plano
            return leerArrayUsers(reader);
        } finally {
            reader.close();
        }
    }

    public List leerArrayUsers(JsonReader reader) throws IOException {
        //Lista temporal
        ArrayList users = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            //Leer objeto
            users.add(leerUser(reader));
        }
        reader.endArray();
        return users;
    }

    //Lee un solo objeto
    public User leerUser(JsonReader reader) throws IOException {
        String username = null;
        String password = null;

        reader.beginObject();
        while(reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "username":
                    username = reader.nextString();
                    break;
                case "pwdsum":
                    password = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new User(username, password);
    }
}
