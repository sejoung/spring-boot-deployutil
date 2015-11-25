

public class Test {
	
	public static void main(String[] args) {
		String key = "/ADP_ADMIN/branches/ADP_ADMIN_UPLUSCLUB/src/com/lgt/adp/manager/ClubBoardManager.java";
		String fileName = key.substring(key.lastIndexOf('/') + 1);
		String path = key.replaceAll("/ADP_ADMIN/branches/ADP_ADMIN_UPLUSCLUB/src","").replaceAll(fileName, "");
		
		
		System.out.println(path);
		
		
		
	}

}
