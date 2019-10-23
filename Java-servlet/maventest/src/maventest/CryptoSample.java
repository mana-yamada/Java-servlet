package maventest;

import org.apache.commons.lang3.RandomStringUtils;

public class CryptoSample {


	public static void main(String[] args) {
		//実際には暗号化と複合化は別々に行われるのが普通なので
		//暗号解読キーとInitial Vectorはファイルに保存しておき、実行時に読みこんで利用する

		//ここでは学習のためにInitial Vectorと暗号解読キーをクラス内に書いておく


		//①データの準備
		//Initial Vector (暗号化時のスタートブロック用の初期値1(128ビット固定長))
		byte[] iv = RandomStringUtils.randomAlphanumeric(16).getBytes();

		//暗号解読キー(128ビット固定長)
		byte[] key = "kawaba_2015_key_".getBytes();

		//②読みこんだInitial Vectorと暗号解読キーはファイルに保存しておく
		//Initial Vectorの保存
		FileUtil.writeBytes(iv,  "iv");
		//暗号解読キーの保存
		FileUtil.writeBytes(key, "secret");

		//③データの表示
		System.out.println("Initial Vector(スタートブロックの初期値=)" + new String(iv) + "(" + iv.length + "byte)" );
		System.out.println("暗号解読キー=" + new String(key) + "(16byte)");

		//④暗号化の処理
		String source = "なんたらかんたら";
		String result = "";
		try {
			//Cryptoオブジェクトを生成する⇒解読キーとInitial Vectorをファイルから読みこむ
			Crypto c = new Crypto(FileUtil.readBytes("secret"), FileUtil.readBytes("iv"));
			//暗号化した文字列を得る
			result = c.encrypto(source);
		}catch(Exception e) {
			e.printStackTrace();
		}
		//暗号文字列を表示
		System.out.println("暗号= " + result);

		//⑤複合化の処理
		try {
			//Cryptoオブジェクトを生成する⇒解読キーとInitial Vectorをファイルから読みこむ
			Crypto c2 = new Crypto(FileUtil.readBytes("secret"), FileUtil.readBytes("iv"));
			//複合化した文字列を得る
			result = c2.decrypto(result);
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("複合=" + result);
	}

}
