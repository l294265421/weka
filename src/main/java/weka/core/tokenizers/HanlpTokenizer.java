package weka.core.tokenizers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

public class HanlpTokenizer extends Tokenizer {

	private Iterator<String> wordsIterator;
	
	@Override
	public String getRevision() {
		return "hanlp 1.3.2";
	}

	@Override
	public String globalInfo() {
		return "hanlp tokenzier";
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
		List<Term> terms = HanLP.segment(s);
		List<String> words = new LinkedList<>();
		for(Term term : terms) {
			if (term != null && (term.nature.startsWith('n') 
					|| term.nature.startsWith('a') || term.nature.startsWith('v'))) {
				words.add(term.word);
			}
		}
		wordsIterator = words.iterator();
	}

}
