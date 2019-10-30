package UsingCrypt;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class UsingCrypt {
	// アルゴリズム/ブロックモード/パディング方式
	  private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	  // 暗号化＆復号化で使用する鍵
	  private static final String ENCRYPT_KEY = "yourEncryptKey01";
	  // 初期ベクトル
	  private static final String INIT_VECTOR = "yourInitVector01";

	  private final IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes());
	  private final SecretKeySpec key = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "AES");


	  /**
	   * passwordを暗号化
	   */
	  public String saveToken(String password) throws Exception {
	   //encryptTokenを呼び出し暗号化ものを返り値として返す
	   return  this.encryptToken(password);

	  }

	  /**
	   * 暗号化処理の詳細
	   */
	  private String encryptToken(String password) throws Exception {

	    Cipher encrypter = Cipher.getInstance(ALGORITHM);
	    encrypter.init(Cipher.ENCRYPT_MODE, this.key, this.iv);
	    byte[] byteToken = encrypter.doFinal(password.getBytes());

	    return new String(Base64.getEncoder().encode(byteToken));
	  }


	  /**
	   * DBに格納した暗号化されているパスワードを複合
	   */
	  public String getToken(String password) throws Exception{
	    return this.decryptToken(password);
	  }

	  /**
	   * 復号化処理の詳細
	   */
	  private String decryptToken(String encryptedToken) throws Exception {

	    Cipher decrypter = Cipher.getInstance(ALGORITHM);
	    decrypter.init(Cipher.DECRYPT_MODE, this.key, this.iv);
	    byte[] byteToken = Base64.getDecoder().decode(encryptedToken);

	    return new String(decrypter.doFinal(byteToken));
	  }


}
