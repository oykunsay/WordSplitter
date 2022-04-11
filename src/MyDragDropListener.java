import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDragDropListener implements DropTargetListener {

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {

	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {

	}

	public void dragExit(DropTargetEvent dte) {

	}

	public void drop(DropTargetDropEvent event) {

		event.acceptDrop(DnDConstants.ACTION_COPY);
		Transferable transferable = event.getTransferable();
		DataFlavor[] flavors = transferable.getTransferDataFlavors();
		for (DataFlavor flavor : flavors) {
			try {
				if (flavor.isFlavorJavaFileListType()) {
					List files = (List) transferable.getTransferData(flavor);
					for (Object file : files) {
						String filepath = ((File) file).getPath();
						String myFile = "/Users/oykuunsay/Desktop/dictionary.txt";
						Trie dictionary = new Trie();
						dictionary.insertDictionary(myFile);
						Splitting splitter = new Splitting(dictionary);
						Map<String, Integer> map = new HashMap<>();
						BufferedReader br = new BufferedReader(new FileReader(filepath));
						String str = br.readLine();
						str = splitter.split(str);

						
						if (str != null) {
							System.out.println(str);
							String[] strArr = str.split("\\s+");
						//	System.out.print("The String Array after splitting is: " + Arrays.toString(strArr));
							String word;
							int wordsLen, i, count, j, k;
							wordsLen = strArr.length;
							for (i = 0; i < wordsLen; i++) {
								word = strArr[i];
								count = 1;
								for (j = i + 1; j < wordsLen; j++) { // search to the last word here
									if (word.equals(strArr[j])) {
										count++;
										for (k = j; k < (wordsLen - 1); k++) {
											strArr[k] = strArr[k + 1];
										}
										wordsLen--;
										j--;
									}
								}
								System.out.println(word + " occurs " + count);
								map.put(word, count);
								count = 0;
							}
						} else {
							System.out.println("Can't split!");
						} 
						System.out.println("\n" + map);
					}

				}

			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		event.dropComplete(true);
	}

}
