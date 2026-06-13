package platformer;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.Test;

import platformer.code.gameengine.loaders.Mapdata;
import platformer.code.gamelogic.GameResources;
import platformer.code.gamelogic.Chess.Piece;
import platformer.code.gamelogic.level.Level;
import platformer.code.gamelogic.level.LevelData;

public class LevelRestartTest {

    @Test
    public void chessPiecesShouldLoadFromClasspath() {
        Piece piece = new Piece(true, "/platformer/code/gamelogic/Chess/wbishop.png");

        assertEquals("Chess piece images should load from the classpath.", true,
                piece.getImage() != null);
    }

    @Test
    public void restartLevelShouldNotAccumulateEnemyObjects() throws Exception {
        GameResources.load();

        Mapdata mapdata = new Mapdata(1, 1, 16, new int[][] {{8}});
        Level level = new Level(new LevelData(mapdata, 0, 0));

        Field enemiesListField = Level.class.getDeclaredField("enemiesList");
        enemiesListField.setAccessible(true);

        @SuppressWarnings("unchecked")
        ArrayList<Object> enemiesList = (ArrayList<Object>) enemiesListField.get(level);

        assertEquals("The level should start with one enemy object.", 1, enemiesList.size());

        level.restartLevel();

        assertEquals("Restarting the level should not keep old enemy objects.", 1, enemiesList.size());
    }
}
