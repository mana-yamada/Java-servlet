//AES暗号、CBCモードによる暗号処理クラス
package maventest;

import java.io.Serializable;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto implements Serializable {

	//暗号化用の暗号器
	private Cipher encrypter;

	//複合化用の暗号器
	private Cipher decrypter;

	//コンストラクタ(引数は暗号解読キーとInitial Vector！！)
	//(String型からbyte[]型に変換したものを指定！！)
	public Crypto(byte[] secretkey, byte[] ivs) throws Exception{

		//暗号化時のスタートブロック用の初期値を作成
		IvParameterSpec iv = new IvParameterSpec(ivs);
		//暗号方式＋解読キーのセットを作成
		SecretKeySpec key = new SecretKeySpec(secretkey, "AES");
		//暗号方式と生成方式などを指定して暗号化用の暗号器を作成
		encrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
		//暗号器を暗号化モードにセットする
		encrypter.init(Cipher.ENCRYPT_MODE, key, iv);

		//複合化用の暗号器を作成
		decrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
		//暗号器を複合化モードにセットする
		decrypter.init(Cipher.DECRYPT_MODE,  key, iv);

	}

	//暗号化処理を実行するメソッド
	public String encrypto (String text) throws Exception{
		//暗号化する
		byte[] crypto = encrypter.doFinal(text.getBytes());
		//表示できるように暗号化したものを文字の配列に変換する
		byte[] str64 = Base64.getEncoder().encode(crypto);
		//表示できるように文字の配列に変換した暗号を文字列化する
		return new String(str64);
	}

	//複合化処理を行うメソッド
	public String decrypto(String str64) throws Exception{
		//暗号文字列をもとのバイナリに戻す
		byte[] str = Base64.getDecoder().decode(str64);
		//複合化する
		byte[] text = decrypter.doFinal(str);
		//文字列に変換して返す
		return new String(text);
	}

}
