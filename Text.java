import org.json.simple.JSONObject;

public class Text {
public static void main(String[] args) {
	CDB obj = new CDB("c:\\DB","good.txt");
	JSONObject jobj = new JSONObject();
	jobj.put("name","good");
	System.out.print(jobj.get("name"));
	obj.putData("1233",jobj,200);
	System.out.print(obj.getData("1233"));
}
}
