import com.tinypc.context.BodySerializerContext;
import com.tinypc.factory.SingletonFactory;
import com.tinyrpc.entity.RpcRequest;
import com.tinyrpc.entity.enumerate.SerializeType;

public class SerializeTest {
    public static void main(String[] args) {
        BodySerializerContext context = SingletonFactory.getInstance(BodySerializerContext.class);

        RpcRequest r = new RpcRequest("asd", "123",
                new Class[]{String.class}, new Object[]{1, 2, 3});

        byte[] bytes = context.serializeBody(r, SerializeType.JDK_SERIALIZE);

        RpcRequest rpcRequest = context.deSerializeBody(bytes, SerializeType.JDK_SERIALIZE);
        System.out.println(rpcRequest);
    }
}
