import com.tinyrpc.entity.enumerate.SerializeType;

public class EnumTest {

    public static void main(String[] args) {
        SerializeType a = SerializeType.JDK_SERIALIZE;
        SerializeType b = SerializeType.JSON_SERIALIZE;
        SerializeType c = SerializeType.PROTOBUF_SERIALIZE;

        System.out.println(a.ordinal());
        System.out.println(c.ordinal());
    }
}
