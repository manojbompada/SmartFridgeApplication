package UserDefinedException;

public class ItemNotFoundException extends Exception {
	private static final long serialVersionUID = 2092342614756796723L;

    private static final String MESSAGE = "Invalid Item Id, please check it again!";

    public ItemNotFoundException(String UUID) {
        super(MESSAGE+"\nitemId:"+UUID);
    }
}
