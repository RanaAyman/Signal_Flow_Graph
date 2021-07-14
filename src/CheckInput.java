import java.util.regex.Matcher;
import java.util.regex.Pattern;
//
public class CheckInput {
	public static boolean ValidInt(String str) {
		String regex = "[+-]?[0-9]+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if (m.find() && m.group().equals(str))
			return true;
		else
			return false;
	}

	public static boolean ValidDouble(String str) {
		String regex = "[+-]?[0-9]+(\\.[0-9]+)?([+-]?[0-9]+)?";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if (m.find() && m.group().equals(str))
			return true;
		else
			return false;
	}
}
