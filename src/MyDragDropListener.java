import java.awt.EventQueue;
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class MyDragDropListener implements DropTargetListener {
	DatabaseOperations dbOper = new DatabaseOperations();
	String myFile = "/Users/oykuunsay/Desktop/dictionary.txt";
	Trie dictionary = new Trie();
	String str;

	public void drop(DropTargetDropEvent event) {

		event.acceptDrop(DnDConstants.ACTION_COPY);
		Transferable transferable = event.getTransferable();
		DataFlavor[] flavors = transferable.getTransferDataFlavors();
		HashMap<String, Integer> map = new HashMap<>();
		for (DataFlavor flavor : flavors) {
			try {
				if (flavor.isFlavorJavaFileListType()) {
					List files = (List) transferable.getTransferData(flavor);
					for (Object file : files) {
						String filepath = ((File) file).getPath();
						dictionary.insertDictionary(myFile);
						Splitting splitter = new Splitting(dictionary);

						BufferedReader br = new BufferedReader(new FileReader(filepath));
						str = br.readLine();
						str = splitter.split(str);

						if (str != null) {
							System.out.println(str);
							String[] strArr = str.split("\\s+");
							String word;
							int wordsLen, i, count, j, k;
							wordsLen = strArr.length;
							for (i = 0; i < wordsLen; i++) {
								word = strArr[i];
								count = 1;
								for (j = i + 1; j < wordsLen; j++) {
									if (word.equals(strArr[j])) {
										count++;
										for (k = j; k < (wordsLen - 1); k++) {
											strArr[k] = strArr[k + 1];
										}
										wordsLen--;
										j--;
									}
								}

								map.put(word, count);
								count = 0;
							}
						} else {
							System.out.println("Can't split!");
						}

					}
				}

			} catch (Exception e) {

				e.printStackTrace();

			}
		}
		try {
			dbOper.insertWordCount(map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new HistogramGraph();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		event.dropComplete(true);
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
	}
}
