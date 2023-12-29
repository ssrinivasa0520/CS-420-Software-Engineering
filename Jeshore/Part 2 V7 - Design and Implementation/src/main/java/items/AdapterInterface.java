package items;

import java.io.IOException;

public interface AdapterInterface {
    void visitItem(ItemInfo selection, String drone) throws IOException, InterruptedException;
    void scanFarm(ItemInfo selection, String drone) throws IOException, InterruptedException;
}
