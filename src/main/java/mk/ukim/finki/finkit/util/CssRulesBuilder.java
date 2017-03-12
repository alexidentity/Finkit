package mk.ukim.finki.finkit.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import com.google.common.collect.Lists;

public class CssRulesBuilder {
	
	private StrBuilder stringBuilder = new StrBuilder();
	
	private String selectorName;
	private List<CssRule> cssRules = Lists.newArrayList();
	
	public CssRulesBuilder(String selectorName) {
		this.selectorName = selectorName;
	}
	
	public String getStyleSheet() {
		stringBuilder.appendln(getStylesForCurrentSelector());
		return stringBuilder.build();
	}
	
	public CssRulesBuilder addRule(String ruleName, String ruleValue) {
		CssRule cssRule = new CssRule();
		cssRule.setRuleName(ruleName);
		cssRule.setRuleValue(ruleValue);
		cssRules.add(cssRule);
		return this;
	}
	
	public CssRulesBuilder newSelector(String selectorName) {
		stringBuilder.appendln(getStylesForCurrentSelector());
		this.selectorName = selectorName;
		this.cssRules = Lists.newArrayList();
		return this;
	}
	
	private String getStylesForCurrentSelector() {
		
		if (this.cssRules.isEmpty()) {
			return StringUtils.EMPTY;
		}
		
  	StrBuilder stringBuilder = new StrBuilder();
  	stringBuilder.appendln(String.format("%s {", selectorName));
  	
  	for (CssRule cssRule : cssRules) {
  		String ruleName = cssRule.getRuleName();
  		String ruleValue = cssRule.getRuleValue();
  		stringBuilder.appendln(String.format("  %s: %s !important;", ruleName, ruleValue));
		}
  	
  	stringBuilder.appendln("}");
  	return stringBuilder.build();
	}
	
	private class CssRule {
  	
  	private String ruleName;
  	private String ruleValue;
  	
		public String getRuleName() {
			return ruleName;
		}
		
		public void setRuleName(String ruleName) {
			this.ruleName = ruleName;
		}
		
		public String getRuleValue() {
			return ruleValue;
		}
		
		public void setRuleValue(String ruleValue) {
			this.ruleValue = ruleValue;
		}
  }
}