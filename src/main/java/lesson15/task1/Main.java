package lesson15.task1;

import lesson15.util.TableMaker;

import java.sql.SQLException;

/**
 *     Спроектировать базу
 * -      Таблица USER содержит поля id, name, birthday, login_ID, city, email, description
 * -      Таблица ROLE содержит поля id, name (принимает значения Administration, Clients, Billing), description
 * -      Таблица USER_ROLE содержит поля id, user_id, role_id
 * Типы полей на ваше усмотрению, возможно использование VARCHAR(255)
 * @author Timofey Yakimov
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        TableMaker maker = new TableMaker("jdbc:h2:~/test", "sa", "");
        maker.createTables();
        maker.close();
    }

}
