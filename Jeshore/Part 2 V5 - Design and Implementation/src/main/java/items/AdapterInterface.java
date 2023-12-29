package items;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public interface AdapterInterface {
    void visitItem(ItemInfo selection) throws IOException, InterruptedException;
    void scanFarm(ItemInfo selection) throws IOException, InterruptedException;
}
