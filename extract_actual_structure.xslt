<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:zenta="http://magwas.rulez.org/zenta"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes" omit-xml-declaration="yes"/>
  <xsl:template match="@*|*|processing-instruction()|comment()">
      <xsl:apply-templates select="package|class|interface"/>
  </xsl:template>

  <xsl:function name="zenta:camelToText">
  	<xsl:param name="camel"/>
  	<xsl:variable name="withSpaces" select="replace($camel, '([A-Z])', ' $1')"/>
  	<xsl:variable name="words">
  		<xsl:for-each select="tokenize($withSpaces,' ')">
  			<xsl:copy-of select="lower-case(.)"/>
  		</xsl:for-each>
  	</xsl:variable>
  	<xsl:copy-of select="string-join($words,' ')"/>  	
  </xsl:function>

  <xsl:template match="/">
  	<folder name="generated structure" id="folder_generated_structure">
      <xsl:apply-templates select="*"/>
  	</folder>
  </xsl:template>  
  <xsl:template match="package">
  	<xsl:variable name="lastpart" select="tokenize(@name,'\.')[last()]"/>
  	<folder name="{@name}" id="package_folder_{@name}">
  	  <element xsi:type="zenta:BasicObject" name="{$lastpart}" id="package_{@name}" ancestor="package"/>
      <xsl:apply-templates select="package|class|interface"/>
  	</folder>
  </xsl:template>

  <xsl:template match="class|interface">
  	<xsl:variable name="package" select="../@name"/>
  	<xsl:variable name="id" select="@qualified"/>
  	<xsl:variable name="type">
	  <xsl:choose>
	  	<xsl:when test="'interface' = name()"
	  	  >service</xsl:when>
	  	<xsl:when test="./annotation/@name='Getter' or ./annotation/@name='Setter' or ./annotation/@name='Entity'"
	  	  >object</xsl:when>
	  	<xsl:otherwise
	  	  >class</xsl:otherwise>
	  </xsl:choose>
  	</xsl:variable>
	<element xsi:type="zenta:BasicObject" name="{@name}" id="{$id}" ancestor="{$type}"/>
	<element xsi:type="zenta:BasicRelationship" id="packageof_{$id}" ancestor="application_component_contains"
		source="package_{$package}" target="{$id}"/>
	<xsl:for-each select="field">
		<xsl:choose>
			<xsl:when test="annotation[@name='Autowired']">
				<xsl:variable name="dependent" select="type/@qualified"/>
				<element xsi:type="zenta:BasicRelationship" id="service_dependency_{$id}_{$dependent}"
					ancestor="service_depends_service"
					source="{$id}" target="{$dependent}"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="modelField">
					<xsl:with-param name="id" select = "$id" />
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:for-each>
	<xsl:for-each select="interface|annotation|class">
	<element xsi:type="zenta:BasicRelationship" id="{name()}_of_{@qualified}_{$id}" ancestor="appcomponent_inherits_appcomponent"
		source="{$id}" target="{@qualified}"/>
	</xsl:for-each>
	<xsl:if test="$type='service'">
		<xsl:apply-templates select="method"/>
	</xsl:if>
  </xsl:template>

<xsl:template name="modelField">
	<xsl:param name="id"/>
	<xsl:variable name="fieldId" select="@qualified"/>
	<element xsi:type="zenta:BasicObject" name="{tokenize($fieldId, '\.')[last()]}" id="{$fieldId}" ancestor="field"/>
	<element xsi:type="zenta:BasicRelationship" id="field_{name()}_of_{$fieldId}_{$id}" ancestor="application_component_contains"
		target="{$fieldId}" source="{$id}"/>
	<xsl:call-template name="enhanceType">
		<xsl:with-param name="fieldId" select = "$fieldId" />
	</xsl:call-template>
</xsl:template>

<xsl:template name="enhanceType">
	<xsl:param name="fieldId"/>
<xsl:message select="$fieldId"/>
	<element xsi:type="zenta:BasicRelationship" id="type_of_{$fieldId}" ancestor="is_of_type"
		source="{$fieldId}" target="{type/@qualified}"/>
	<xsl:for-each select="type/generic">
<xsl:message select="'generic'"/>
		<element xsi:type="zenta:BasicRelationship" name="{position()}" id="generic_of_{$fieldId}_{@qualified}"
			ancestor="has_generic"
			source="{$fieldId}" target="{@qualified}"/>
	</xsl:for-each>
</xsl:template>

  <xsl:template match="method">
  	<xsl:variable name="package" select="../../@name"/>
  	<xsl:variable name="interface" select="../@name"/>
  	<xsl:variable name="id" select="@qualified"/>
    <xsl:variable name="name" select="zenta:camelToText(@name)"/>
  	<xsl:variable name="serviceId" select="concat($package, '.', $interface)"/>
	<element xsi:type="zenta:BasicObject" name="{$name}" id="{$id}" ancestor="service_method"/>
	<element xsi:type="zenta:BasicRelationship" id="serviceof_{$id}" ancestor="application_component_contains"
		source="{$serviceId}" target="{$id}"/>
	<element xsi:type="zenta:BasicRelationship" id="outputof_{$id}" ancestor="outputs"
		source="{$id}" target="{return/@qualified}"/>
    <xsl:for-each select="parameter">
		<element xsi:type="zenta:BasicRelationship" name="{@name}" id="serviceof_{$id}" ancestor="parameter"
			target="{$id}" source="{type/@qualified}"/>    	
		<xsl:call-template name="enhanceType">
			<xsl:with-param name="fieldId" select = "$id" />
		</xsl:call-template>
    </xsl:for-each>
  </xsl:template>
</xsl:stylesheet>

