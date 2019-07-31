import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;


public class CDB {	
private String path="c:";
CDB(String filename){
this.path = this.path+"//"+filename;
try {
createFile();
} catch (Exception e) {
}	
}
CDB(String path,String filename) {
path = path+"//"+filename;
this.path=path;
try {
createFile();
} catch (Exception e) {
}
}
private boolean createFile() throws Exception{
File file = new File(path);
        if(file.createNewFile()){
        return true;
        }else{
        
    throw new Exception("Already Exist");
        } 

        
}
private boolean writeData(String data){
try{    
BufferedWriter out = new BufferedWriter( 
                  new FileWriter(path, true));  
       out.append(data);    
       out.close();    
}catch(Exception e){
}    
return true;
  
}
private String readData() throws Exception{
StringBuilder data= new StringBuilder();
data.append("");
FileReader fr;
fr = new FileReader(path);
int i;    
       while((i=fr.read())!=-1)    
       data.append((char)i);    
       fr.close();   

return data.toString();
}
public String putData(String key,JSONObject jdata){
String data = jdata.toString(); 
if(getData(key).containsKey("error")){
if(getData(key).get("error").equals("not found")){
writeData(key+":"+data+"\n");
return "inserted successfully";
}else{
return "already Exist";
}
}
return "already Exict";
}
public String putData(String key,JSONObject jdata,Integer seconds){
String data = jdata.toString(); 
if(getData(key).containsKey("error")){
if(getData(key).get("error").equals("not found")){
writeData(key+":"+seconds*1000+":"+System.currentTimeMillis()+":"+data+"\n");
return "inserted successfully";
}else{
return "already Exist";
}
}else{
return "already Exist";
}

}
public String delete(String key){
try{
String[] lines = readData().split("\n");
StringBuilder data = new StringBuilder();
for(String line:lines){
if(line.contains(":")){
String tkey = line.substring(0, line.indexOf(':'));
if(!tkey.equals(key)){
data.append(line+"\n");
}
}
}   
FileWriter fw =  new FileWriter(path);  
       fw.write(data.toString());    
       fw.close();    
}catch(Exception e){
return e.toString();
}  
return "SUCCESS";
}
@SuppressWarnings("unchecked")
public JSONObject getData(String key){
JSONObject error = new JSONObject();
try{
String data = readData();
if(data!=""){
String[] lines = data.split("\n");
for(String line: lines){
if(line.contains("{")){
String[] params = line.substring(0, line.indexOf('{')).split(":");
String tkey = params[0];
if(tkey.equals(key)){
if(params.length>1){
int seconds = Integer.parseInt(params[1]);
long start = Long.parseLong(params[2]);
long end =System.currentTimeMillis();
if(end-start>seconds){
error.put("error", "time out by " + (((end-start)-seconds)/1000) + " seconds");
return error;
}
}
Object obj = new JSONParser().parse(line.substring(line.indexOf('{'))); 
       JSONObject jobj = (JSONObject) obj;
       return jobj;
}
}
}
}
}catch(Exception e){
error.put("error", e.toString());
return error;
}

error.put("error", "not found");
return error;
}
@SuppressWarnings("unchecked")
public static void main(String[] args) throws Exception {
CDB cobj = new CDB("C://DB","dbfile.txt");
JSONObject jobj = new JSONObject();
jobj.put("name", "apple");
jobj.put("age", 21);
jobj.put("gender", "female");
cobj.putData("2", jobj);
cobj.putData("apple",jobj,30);
cobj.delete("4");
cobj.putData("5",jobj);
System.out.println(cobj.getData("5"));
    }
}

	

