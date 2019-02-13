<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE xslt>
<xsl:stylesheet version="2.0"
	xmlns:zenta="http://magwas.rulez.org/zenta"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="xslt/functions.xslt"/>
	<xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes" omit-xml-declaration="yes"/>

	<xsl:param name="issuesfile"/>
	<xsl:param name="modelfile"/>
	<xsl:param name="missingissuefile"/>

	<xsl:variable name="issues" select="document($issuesfile)"/>

	<xsl:variable name="model" select="document($modelfile)"/>
	<xsl:variable name="compactedIssues">
		<xsl:apply-templates select="//entry"/>
	</xsl:variable>
	<xsl:variable name="existingIssues" select="$compactedIssues/issue[@githubIssue and @status='open']"/>

	<xsl:template match="/">
		<xsl:result-document href="{$missingissuefile}">
			<xsl:copy-of select="$compactedIssues/issue[not(@githubIssue)]"/>
		</xsl:result-document>
		<xsl:variable name="prios" select="zenta:prioritizeIssues(/dummy,$existingIssues,1)"/>
		<xsl:copy-of select="$prios"/>
	</xsl:template>	
	<xsl:template match="issue" mode="justname">
		<xsl:copy>
			<xsl:copy-of select="@name|@issueTitle"/>
		</xsl:copy>
	</xsl:template>
	<xsl:template match="@*|*|processing-instruction()|comment()" mode="justname">
	    <xsl:copy>
	      <xsl:apply-templates select="*|@*|text()|processing-instruction()|comment()"/>
	    </xsl:copy>
    </xsl:template>
	
	<xsl:function name="zenta:prioritizeIssues">
		<xsl:param name="prioritized"/>
		<xsl:param name="nonprioritized"/>
		<xsl:param name="depth"/>
		<xsl:choose>
			<xsl:when test="$depth > 5 or not($nonprioritized)">
				<result>
					<xsl:attribute name="depth" select="$depth"/>
					<xsl:apply-templates select="$prioritized" mode="justname"/>
					<left>
						<xsl:apply-templates select="$nonprioritized" mode="justname"/>
					</left>
				</result>
			</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="newprioritized" select="$nonprioritized[not(value/@target = $nonprioritized/@operationid)]"/>
				<xsl:variable name="newnonprioritized" select="$nonprioritized[not(@errorID = $newprioritized/@errorID)]"/>
				<xsl:copy-of select="zenta:prioritizeIssues($prioritized|$newprioritized,$newnonprioritized,$depth+1)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:function>
	<xsl:template match="entry">
		<xsl:if test="object/testcase">
			<xsl:variable name="entry" select="."/>
			<xsl:variable name="errorUrl" select="./@errorURL"/>
			<xsl:variable name="issue" select="$issues//link[@url=$errorUrl]/.."/>
			<xsl:variable name="operationid" select="object/testcase/@operationid"/>
			<issue>
				<xsl:copy-of select="object/testcase/@*"/>
				<xsl:copy-of select="@*"/>
				<xsl:if test="$issue/@url">
					<xsl:attribute name="githubIssue" select="$issue/@url"/>
					<xsl:attribute name="issueTitle" select="$issue/summary"/>
				</xsl:if>
				<xsl:copy-of select="$issue/@*"/>
				<xsl:copy-of select="$model//element[
					@id=$operationid]/value[@ancestorName='depends on/supports'
						and @direction=1]"/>
			</issue>
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>

