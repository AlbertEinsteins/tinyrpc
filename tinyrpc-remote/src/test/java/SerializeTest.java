import com.tinyrpc.core.context.BodySerializerContext;
import com.tinyrpc.core.factory.SingletonFactory;
import com.tinyrpc.core.entity.RpcRequest;
import com.tinyrpc.core.entity.enumerate.SerializeType;

public class SerializeTest {
    public static void main(String[] args) {
        BodySerializerContext context = SingletonFactory.getInstance(BodySerializerContext.class);

//        RpcRequest r = new RpcRequest("asd", "123",
//                new Class[]{String.class}, new Object[]{1, 2, 3});
//
//        byte[] bytes = context.serializeBody(r, SerializeType.JDK_SERIALIZE);
//
//        RpcRequest rpcRequest = (RpcRequest) context.deSerializeBody(bytes, SerializeType.JDK_SERIALIZE);
//        System.out.println(rpcRequest);
    }
}
