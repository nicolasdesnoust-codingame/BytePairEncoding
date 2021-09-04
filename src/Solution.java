import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            stringBuilder.append(in.next());
        }
        byte[] inputBytes = stringBuilder.toString().getBytes();
        
        byte availableNonTerminalByte = 'Z';
        List<ProductionRule> productionRules = new ArrayList<>();
        boolean hasPairs = true;

        while(hasPairs) {
            Optional<byte[]> mostCommonPairOptional = findMostCommonPair(inputBytes);
            
            if(mostCommonPairOptional.isPresent()) {
                byte[] mostCommonPair = mostCommonPairOptional.get();
                inputBytes = replace(inputBytes, mostCommonPair, availableNonTerminalByte);
                productionRules.add(new ProductionRule(availableNonTerminalByte, mostCommonPair));
                availableNonTerminalByte--;
                System.err.println(new String(mostCommonPair) + " " + new String(inputBytes));
            }
            else {
                System.err.println("No more pairs");
                hasPairs = false;
            }
        }

        String outputString = new String(inputBytes);
        System.out.println(outputString);
        productionRules.forEach(System.out::println);
    }
	
    private static Optional<byte[]> findMostCommonPair(byte[] inputBytes) {
        int mostCommonPairOccurences = -1;
        byte[] mostCommonPair = null;

        for(int i = 0; i < inputBytes.length - 3; i++) {
            byte[] currentArray = Arrays.copyOfRange(inputBytes, i, inputBytes.length);
            int currentPairOccurences = countFirstPairOccurences(currentArray);
            if(currentPairOccurences > mostCommonPairOccurences) {
                mostCommonPairOccurences = currentPairOccurences;
                mostCommonPair = new byte[]{ currentArray[0], currentArray[1] };
                System.err.println("Found ("+ currentPairOccurences +") better pair: " + new String(mostCommonPair));
            }
        }

		return mostCommonPairOccurences > 1 ? Optional.of(mostCommonPair) : Optional.empty();
	}

    private static int countFirstPairOccurences(byte[] inputBytes) {
        int occurences = 1;
        byte[] firstPair = { inputBytes[0], inputBytes[1] };

        for(int i = 2; i < inputBytes.length - 1; i ++) {
            byte[] currentPair = { inputBytes[i], inputBytes[i + 1] };
            if(firstPair[0] == currentPair[0] 
            && firstPair[1] == currentPair[1]) {
                occurences++;
                i++;
            }
        }

		return occurences;
	}

	private static byte[] replace(byte[] inputBytes, byte[] mostCommonPair, byte availableNonTerminalByte) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for(int i = 0; i < inputBytes.length; i++) {
            if(i == inputBytes.length - 1) {
                baos.write(new byte[]{ inputBytes[i] }, 0, 1);
            }
            else if(inputBytes[i] == mostCommonPair[0]
            && inputBytes[i + 1] == mostCommonPair[1]) {
                i = i + 1;
                baos.write(new byte[]{ availableNonTerminalByte }, 0, 1);
            }
            else {
                baos.write(new byte[]{ inputBytes[i] }, 0, 1);
            }
        }

		return baos.toByteArray();
	}
}