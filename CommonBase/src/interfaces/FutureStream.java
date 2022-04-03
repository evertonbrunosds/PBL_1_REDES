package interfaces;

import java.io.IOException;

@FunctionalInterface
public interface FutureStream<P> {
    
    void then(final P param) throws IOException;
    
}
