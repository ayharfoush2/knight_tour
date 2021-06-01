package boardgame.database;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;


/**
 * This is the database class for storing files or result to the database
 */
public class Database {
    /**
     *  instance for establishing connection to a remote SQL database.
     */
    public static Jdbi jdbi = Jdbi.create(
            "jdbc:postgresql://batyr.db.elephantsql.com:5432/",
            "tkjajuhb",
            "vojCoHtKDyqeb4ketIAjE_aNkgZHk98X"
    )
            .installPlugin(new SqlObjectPlugin())
            .installPlugin(new PostgresPlugin())
            .setSqlLogger(new Slf4JSqlLogger());

    /**
     * Persist {Game} instance into the database by a { org.jdbi.v3.core.Jdbi} Object.
     *
     * @param game {Game} instance to be persisted or stored into the database.
     */
    public static void persistGame(Game game){
        try { Class.forName("org.postgresql.Driver"); }catch (ClassNotFoundException e){ e.printStackTrace(); }
        Logger.debug("Database Connection Established");
        jdbi.withExtension(GameDao.class, dao -> dao.insert(game));
        Logger.info("Game was saved Successfully");
    }
}
