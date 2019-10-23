package dosecret;

//暗号キーやInitial Vectorを保存する「FileUtil」クラス


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SecretSave {

	//OutputStreamを用いて暗号化されたバイナリを指定したパスへ送り書き込む
	public static void writeBytes(byte[] b, String Path) {
		try(ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(Path))) {
			o.write(b);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//InputStreamを用いて暗号化されたバイナリを指定したパスで読みこむ
	public static byte[] readBytes(String Path) {
		byte[] b = new byte[16];
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(Path))) {
			in.read(b, 0, 16);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}
