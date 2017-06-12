package weka.core.tokenizers;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import weka.core.Option;
import weka.core.Utils;

public class HanlpTokenizer extends Tokenizer {

	private static final long serialVersionUID = -8969384620055979993L;
	
	/**
	 * 磁性前缀集合，通过,(英文逗号隔开)，all表示所有词性
	 */
	private String partOfSpeachs = "all";
	
	private String[] splitString;
	private int currentPosition;
	private int maxPosition;
	
	public String getPartOfSpeachs() {
		return partOfSpeachs;
	}

	public void setPartOfSpeachs(String partOfSpeachs) {
		this.partOfSpeachs = partOfSpeachs;
	}

	@Override
	public Enumeration listOptions() {
		Vector<Option> result = new Vector<>();
		result.add(new Option("分词后保留的词性前缀", "partOfSpeachs", 1, "-p"));
		return result.elements();
	}

	@Override
	public String[] getOptions() {
	    Vector<String>	result;
	    result = new Vector<String>();
	    result.add("-partOfSpeach");
	    result.add(getPartOfSpeachs());
	    return result.toArray(new String[result.size()]);
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		String tmp = Utils.getOption("partOfSpeach", options);
		if (tmp.length() == 0) {
			setPartOfSpeachs("all");
		} else {
			setPartOfSpeachs(tmp);
		}
		super.setOptions(options);
	}

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
		return currentPosition <= maxPosition;
	}

	@Override
	public Object nextElement() {
		return splitString[currentPosition++];
	}

	@Override
	public void tokenize(String s) {
		List<Term> terms = HanLP.segment(s);
		List<String> words = new LinkedList<>();
		for(Term term : terms) {
			if (term != null && term.word != null && term.word.length() != 0 
					&& acceptByPartOfSpeach(term.nature.name())) {
				words.add(term.word);
			}
		}
		splitString = words.toArray(new String[words.size()]);
		maxPosition = words.size() - 1;
		currentPosition = 0;
	}

	private boolean acceptByPartOfSpeach(String partOfSpeach) {
		if (partOfSpeach == null || partOfSpeach.length() == 0) {
			return false;
		}
		if ("all".equals(this.partOfSpeachs)) {
			return true;
		}
		String[] tmps = this.partOfSpeachs.split(",");
		for (String tmp : tmps) {
			if (partOfSpeach.startsWith(tmp)) {
				return true;
			}
		}
		return false;
	}
}
