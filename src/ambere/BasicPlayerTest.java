package ambere;

import java.io.File;
import java.io.PrintStream;
import java.util.Map;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

public class BasicPlayerTest implements BasicPlayerListener {
    
    private PrintStream out = null;
    public BasicController control;

    public BasicPlayerTest() {
        out = System.out;
    }
    
    public void play(String filename){
        BasicPlayer player = new BasicPlayer();
        control = player;
        
        player.addBasicPlayerListener(this);
        
        try {
            control.open(new File(filename));
            control.play();
            
            control.setGain(0.85);
            control.setPan(0.0);
            
        } catch (BasicPlayerException e) {
            System.out.println(e.getMessage());
        }
    }
    public void display(String msg){
        if (out != null) {
            out.println(msg);
        }
    }
    @Override
    public void opened(Object o, Map map) {
        display("opend: "+map.toString());
    }

    @Override
    public void progress(int i, long l, byte[] bytes, Map map) {
        display("progreso: "+map.toString());
    }

    @Override
    public void stateUpdated(BasicPlayerEvent bpe) {
        display("estado actualizado: "+bpe.toString());
    }

    @Override
    public void setController(BasicController bc) {
        display("setcontroller: "+bc.toString());
    }
    
}
