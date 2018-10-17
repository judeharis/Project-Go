package Go.SlickGo;

import java.util.ArrayList;

class StoneStringResponse {
    public final boolean state;
    public final ArrayList<Tuple> list;

    public StoneStringResponse(boolean state, ArrayList<Tuple> list) {
        this.state = state;
        this.list = list;
    }
}
