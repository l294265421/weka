package weka.core.tokenizers;

import java.util.Iterator;
import java.util.List;

import com.liyuncong.algorithm.usestanfordsegmenter.StanfordSegmenter;

/**
 * 斯坦福分词器
 * @author yuncong
 *
 */
public class StanfordWordTokenizer extends Tokenizer {
	private Iterator<String> wordsIterator;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4096832873197179324L;

	@Override
	public String getRevision() {
		return "Stanford Segmenter 3.5.2";
	}

	@Override
	public String globalInfo() {
		return "Stanford Segmenter";
	}

	@Override
	public boolean hasMoreElements() {
		return wordsIterator.hasNext();
	}

	@Override
	public Object nextElement() {
		return wordsIterator.next();
	}

	@Override
	public void tokenize(String s) {
		List<String> words = StanfordSegmenter.textSeg(s);
		wordsIterator = words.iterator();
	}

}
