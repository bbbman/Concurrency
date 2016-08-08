import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/** 
 *   CommentedProperties
 *   ���Properties������չ�Ĺ�����
 *   
 *   ��չ��������Ҫ����:
 *   1.��Properties�ļ���ע�͵ı��档
 *       CommentedProperties�ڶ�ȡ�ͱ���Properties�ļ�ʱ,�Ὣ��ע��
 *       һ���ȡ���档CommentedProperties�л��ṩ����������key��ȡ
 *       ��Ӧע�͡���CommentedProperties�����һ��K-V��ʱ��Ҳ���ṩ
 *       �����Ӧע�͵ķ�����
 *       
 *   2.��Properties�ļ���Keyֵ˳��ı�֤��
 *       CommentedProperties�ᱣ֤Key��˳�򡣴�һ��Properties�ļ���
 *       ��ȡ����K-V�ԣ����浽��һ��Properties�ļ�ʱ��Key��˳�򲻻�
 *       �ı䡣
 *       
 *
 * @author BrokenDreams
 */
public class CommentedProperties{

	/**
	 * �ڲ����Ա�
	 */
	private final Properties props;

	/**
	 * ����key��comment��ӳ�䣬
	 * ͬʱ�������ӳ������֤key��˳��
	 */
	private final LinkedHashMap<String, String> keyCommentMap = new LinkedHashMap<String, String>();

	private static final String BLANK = "";

	public CommentedProperties() {
		super();
		props = new Properties();
	}

	public CommentedProperties(Properties defaults) {
		super();
		props = new Properties(defaults);
	}

	/**
	 * ����һ�����ԣ����key�Ѿ����ڣ���ô�����Ӧvalueֵ���ǡ�
	 * @param key
	 * @param value
	 * @return
	 */
	public String setProperty(String key, String value) {
		return setProperty(key, value, BLANK);
	}

	/**
	 * ����һ�����ԣ����key�Ѿ����ڣ���ô�����Ӧvalueֵ���ǡ�
	 * 
	 * @param key ��
	 * @param value �����Ӧ��ֵ
	 * @param comment �Լ�ֵ�Ե�˵��
	 * @return
	 */
	public synchronized String setProperty(String key, String value, String comment){
		Object oldValue = props.setProperty(key, value);
		if(BLANK.equals(comment)){
			if(!keyCommentMap.containsKey(key)){
				keyCommentMap.put(key, comment);
			}
		}else{
			keyCommentMap.put(key, comment);
		}
		return (String)oldValue;
	}
	
	/**
	 * ����key��ȡ���Ա�����Ӧ��value��
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return props.getProperty(key);
	}

	/**
	 * ����key��ȡ���Ա�����Ӧ��value��
	 * ���û�ҵ���Ӧ��value������defaultValue��
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

	/**
	 * ��һ���ַ����ж�ȡ���Ե����Ա���
	 * 
	 * @param reader
	 * @throws IOException
	 */
	public synchronized void load(Reader reader) throws IOException {
		load0(new LineReader(reader));
	}

	/**
	 * ��һ���ֽ����ж�ȡ���Ե����Ա���
	 * 
	 * @param inStream
	 * @throws IOException
	 */
	public synchronized void load(InputStream inStream) throws IOException {
		load0(new LineReader(inStream));
	}

	/**
	 * ��һ���ֽ����ж�ȡ���Ե����Ա���
	 * 
	 * @param inStream
	 * @param charset
	 * @throws IOException
	 */
	public synchronized void load(InputStream inStream, String charset) throws IOException {
		InputStreamReader reader = new InputStreamReader(inStream, charset);
		load0(new LineReader(reader));
	}

	/**
	 * ��һ���ļ��ж�ȡ���Ե����Ա���
	 * 
	 * @param file �����ļ�
	 * @param charset �ַ���
	 * @throws IOException
	 */
	public synchronized void load(File file, String charset) throws IOException {
		FileInputStream inputStream = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(inputStream, charset);
		load0(new LineReader(reader));
	}

	/**
	 * ��һ���ļ��ж�ȡ���Ե����Ա���
	 * Ĭ���ַ���Ϊutf-8
	 * 
	 * @param file �����ļ�
	 * @throws IOException
	 */
	public synchronized void load(File file) throws IOException {
		FileInputStream inputStream = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
		load0(new LineReader(reader));
	}
	
	/**
	 * �����Ա��е�����д���ַ������档
	 * 
	 * @param writer
	 * @throws IOException
	 */
	public void store(Writer writer) throws IOException {
		store0((writer instanceof BufferedWriter)?(BufferedWriter)writer
				: new BufferedWriter(writer),false);
	}

	/**
	 * �����Ա��е�����д���ֽ������档
	 * 
	 * @param out
	 * @throws IOException
	 */
	public void store(OutputStream out) throws IOException {
		store0(new BufferedWriter(new OutputStreamWriter(out, "utf-8")),true);
	}

	/**
	 * ������Ա���ĳ��key��Ӧ��valueֵ�Ͳ���value��ͬ
	 * ��ô����true�����򷵻�false��
	 * 
	 * @param value
	 * @return
	 */
	public boolean containsValue(String value) {
		return props.containsValue(value);
	}

	/**
	 * ������Ա��д��ڲ���key������true�����򷵻�false��
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key) {
		return props.containsKey(key);
	}
	
	/**
	 * ��ȡ���Ա��м�ֵ������
	 * @return
	 */
	public int size() {
		return props.size();
	}

	/**
	 * ������Ա��Ƿ�Ϊ��
	 * @return
	 */
	public boolean isEmpty() {
		return props.isEmpty();
	}

	/**
	 * ������Ա�
	 */
	public synchronized void clear() {
		props.clear();
		keyCommentMap.clear();
	}

	/**
	 * ��ȡ���Ա�������key�ļ��ϡ�
	 * 
	 * @return
	 */
	public Set<String> propertyNames() {
		return props.stringPropertyNames();
	}


	public synchronized String toString() {
		StringBuffer buffer = new StringBuffer();
		Iterator<Map.Entry<String, String>> kvIter = keyCommentMap.entrySet().iterator();
		buffer.append("[");
		while(kvIter.hasNext()){
			buffer.append("{");
			Map.Entry<String, String> entry = kvIter.next();
			String key = entry.getKey();
			String val = getProperty(key);
			String comment = entry.getValue();
			buffer.append("key="+key+",value="+val+",comment="+comment);
			buffer.append("}");
		}
		buffer.append("]");
		return buffer.toString();
	}

	public boolean equals(Object o) {
		//������ע��˵���Ƿ���ͬ
		return props.equals(o);
	}

	public int hashCode() {
		return props.hashCode();
	}
	
	private void load0(LineReader lr) throws IOException {
		char[] convtBuf = new char[1024];
		int limit;
		int keyLen;
		int valueStart;
		char c;
		boolean hasSep;
		boolean precedingBackslash;
		StringBuffer buffer = new StringBuffer();

		while ((limit = lr.readLine()) >= 0) {
			c = 0;
			keyLen = 0;
			valueStart = limit;
			hasSep = false;
			//��ȡע��
			c = lr.lineBuf[keyLen];
			if(c == '#' || c == '!'){
				String comment = loadConvert(lr.lineBuf, 1, limit - 1, convtBuf);
				if(buffer.length() > 0){
					buffer.append("\n");
				}
				buffer.append(comment);
				continue;
			}
			precedingBackslash = false;
			while (keyLen < limit) {
				c = lr.lineBuf[keyLen];
				//need check if escaped.
				if ((c == '=' ||  c == ':') && !precedingBackslash) {
					valueStart = keyLen + 1;
					hasSep = true;
					break;
				} else if ((c == ' ' || c == '\t' ||  c == '\f') && !precedingBackslash) {
					valueStart = keyLen + 1;
					break;
				} 
				if (c == '\\') {
					precedingBackslash = !precedingBackslash;
				} else {
					precedingBackslash = false;
				}
				keyLen++;
			}
			while (valueStart < limit) {
				c = lr.lineBuf[valueStart];
				if (c != ' ' && c != '\t' &&  c != '\f') {
					if (!hasSep && (c == '=' ||  c == ':')) {
						hasSep = true;
					} else {
						break;
					}
				}
				valueStart++;
			}
			String key = loadConvert(lr.lineBuf, 0, keyLen, convtBuf);
			String value = loadConvert(lr.lineBuf, valueStart, limit - valueStart, convtBuf);
			setProperty(key, value, buffer.toString());
			//reset buffer
			buffer = new StringBuffer();
		}
	}

	/* 
	 * ����java.util.Properties.LineReader���и���
	 * 
	 * Read in a "logical line" from an InputStream/Reader, skip all comment
	 * and blank lines and filter out those leading whitespace characters 
	 * (\u0020, \u0009 and \u000c) from the beginning of a "natural line". 
	 * Method returns the char length of the "logical line" and stores 
	 * the line in "lineBuf". 
	 */
	class LineReader {
		public LineReader(InputStream inStream) {
			this.inStream = inStream;
			inByteBuf = new byte[8192]; 
		}

		public LineReader(Reader reader) {
			this.reader = reader;
			inCharBuf = new char[8192]; 
		}

		byte[] inByteBuf;
		char[] inCharBuf;
		char[] lineBuf = new char[1024];
		int inLimit = 0;
		int inOff = 0;
		InputStream inStream;
		Reader reader;

		int readLine() throws IOException {
			int len = 0;
			char c = 0;

			boolean skipWhiteSpace = true;
			boolean isNewLine = true;
			boolean appendedLineBegin = false;
			boolean precedingBackslash = false;
			boolean skipLF = false;

			while (true) {
				if (inOff >= inLimit) {
					inLimit = (inStream==null)?reader.read(inCharBuf)
							:inStream.read(inByteBuf);
					inOff = 0;
					if (inLimit <= 0) {
						if (len == 0) { 
							return -1; 
						}
						return len;
					}
				}     
				if (inStream != null) {
					//The line below is equivalent to calling a 
					//ISO8859-1 decoder.
					c = (char) (0xff & inByteBuf[inOff++]);
				} else {
					c = inCharBuf[inOff++];
				}
				if (skipLF) {
					skipLF = false;
					if (c == '\n') {
						continue;
					}
				}
				if (skipWhiteSpace) {
					if (c == ' ' || c == '\t' || c == '\f') {
						continue;
					}
					if (!appendedLineBegin && (c == '\r' || c == '\n')) {
						continue;
					}
					skipWhiteSpace = false;
					appendedLineBegin = false;
				}
				if (isNewLine) {
					isNewLine = false;
				}

				if (c != '\n' && c != '\r') {
					lineBuf[len++] = c;
					if (len == lineBuf.length) {
						int newLength = lineBuf.length * 2;
						if (newLength < 0) {
							newLength = Integer.MAX_VALUE;
						}
						char[] buf = new char[newLength];
						System.arraycopy(lineBuf, 0, buf, 0, lineBuf.length);
						lineBuf = buf;
					}
					//flip the preceding backslash flag
					if (c == '\\') {
						precedingBackslash = !precedingBackslash;
					} else {
						precedingBackslash = false;
					}
				}
				else {
					// reached EOL
					if (len == 0) {
						isNewLine = true;
						skipWhiteSpace = true;
						len = 0;
						continue;
					}
					if (inOff >= inLimit) {
						inLimit = (inStream==null)
								?reader.read(inCharBuf)
										:inStream.read(inByteBuf);
								inOff = 0;
								if (inLimit <= 0) {
									return len;
								}
					}
					if (precedingBackslash) {
						len -= 1;
						//skip the leading whitespace characters in following line
						skipWhiteSpace = true;
						appendedLineBegin = true;
						precedingBackslash = false;
						if (c == '\r') {
							skipLF = true;
						}
					} else {
						return len;
					}
				}
			}
		}
	}

	/*
	 * Converts encoded &#92;uxxxx to unicode chars
	 * and changes special saved chars to their original forms
	 */
	private String loadConvert (char[] in, int off, int len, char[] convtBuf) {
		if (convtBuf.length < len) {
			int newLen = len * 2;
			if (newLen < 0) {
				newLen = Integer.MAX_VALUE;
			} 
			convtBuf = new char[newLen];
		}
		char aChar;
		char[] out = convtBuf; 
		int outLen = 0;
		int end = off + len;

		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				aChar = in[off++];   
				if(aChar == 'u') {
					// Read the xxxx
					int value=0;
					for (int i=0; i<4; i++) {
						aChar = in[off++];  
						switch (aChar) {
						case '0': case '1': case '2': case '3': case '4':
						case '5': case '6': case '7': case '8': case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a': case 'b': case 'c':
						case 'd': case 'e': case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A': case 'B': case 'C':
						case 'D': case 'E': case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed \\uxxxx encoding.");
						}
					}
					out[outLen++] = (char)value;
				} else {
					if (aChar == 't') aChar = '\t'; 
					else if (aChar == 'r') aChar = '\r';
					else if (aChar == 'n') aChar = '\n';
					else if (aChar == 'f') aChar = '\f'; 
					out[outLen++] = aChar;
				}
			} else {
				out[outLen++] = (char)aChar;
			}
		}
		return new String (out, 0, outLen);
	}

	private void store0(BufferedWriter bw, boolean escUnicode)
			throws IOException{
		synchronized (this) {
			Iterator<Map.Entry<String, String>> kvIter = keyCommentMap.entrySet().iterator();
			while(kvIter.hasNext()){
				Map.Entry<String, String> entry = kvIter.next();
				String key = entry.getKey();
				String val = getProperty(key);
				String comment = entry.getValue();
				key = saveConvert(key, true, escUnicode);
				/* No need to escape embedded and trailing spaces for value, hence
				 * pass false to flag.
				 */
				val = saveConvert(val, false, escUnicode);
				if(!comment.equals(BLANK))
					writeComments(bw, comment);
				bw.write(key + "=" + val);
				bw.newLine();
			}
		}
		bw.flush();
	}

	private static void writeComments(BufferedWriter bw, String comments) 
			throws IOException {
		bw.write("#");
		int len = comments.length();  
		int current = 0;
		int last = 0;
		while (current < len) {
			char c = comments.charAt(current);
			if (c > '\u00ff' || c == '\n' || c == '\r') {
				if (last != current) 
					bw.write(comments.substring(last, current));
				if (c > '\u00ff') {
					bw.write(c);
				} else {
					bw.newLine();
					if (c == '\r' && 
							current != len - 1 && 
							comments.charAt(current + 1) == '\n') {
						current++;
					}
					if (current == len - 1 ||
							(comments.charAt(current + 1) != '#' &&
							comments.charAt(current + 1) != '!'))
						bw.write("#");
				}
				last = current + 1;
			} 
			current++;
		}
		if (last != current) 
			bw.write(comments.substring(last, current));
		bw.newLine();
	}

	/*
	 * Converts unicodes to encoded &#92;uxxxx and escapes
	 * special characters with a preceding slash
	 */
	private String saveConvert(String theString,
			boolean escapeSpace,
			boolean escapeUnicode) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for(int x=0; x<len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\'); outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch(aChar) {
			case ' ':
				if (x == 0 || escapeSpace) 
					outBuffer.append('\\');
				outBuffer.append(' ');
				break;
			case '\t':outBuffer.append('\\'); outBuffer.append('t');
			break;
			case '\n':outBuffer.append('\\'); outBuffer.append('n');
			break;
			case '\r':outBuffer.append('\\'); outBuffer.append('r');
			break;
			case '\f':outBuffer.append('\\'); outBuffer.append('f');
			break;
			case '=': // Fall through
			case ':': // Fall through
			case '#': // Fall through
			case '!':
				outBuffer.append('\\'); outBuffer.append(aChar);
				break;
			default:
				if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode ) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex((aChar >> 12) & 0xF));
					outBuffer.append(toHex((aChar >>  8) & 0xF));
					outBuffer.append(toHex((aChar >>  4) & 0xF));
					outBuffer.append(toHex( aChar        & 0xF));
				} else {
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	/**
	 * Convert a nibble to a hex character
	 * @param	nibble	the nibble to convert.
	 */
	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}

	/** A table of hex digits */
	private static final char[] hexDigit = {
		'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
	};

}
