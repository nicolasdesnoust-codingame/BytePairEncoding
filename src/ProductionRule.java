
public class ProductionRule {
    public byte nonTerminalByte;
    public byte[] bytesToReplace;

    public ProductionRule(byte nonTerminalByte, byte[] bytesToReplace) {
        this.nonTerminalByte = nonTerminalByte;
        this.bytesToReplace = bytesToReplace;
    }

    @Override
    public String toString() {
        return new String(new byte[] { nonTerminalByte }) + " = " + new String(bytesToReplace);
    }
}