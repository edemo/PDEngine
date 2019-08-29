<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:zenta="http://magwas.rulez.org/zenta"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="1.0" encoding="utf-8"
		indent="yes" omit-xml-declaration="yes" />
	<xsl:template
		match="@*|*|processing-instruction()|comment()">
		<xsl:apply-templates
			select="package|class|interface" />
	</xsl:template>

	<xsl:function name="zenta:camelToText">
		<xsl:param name="camel" />
		<xsl:variable name="withSpaces"
			select="replace($camel, '([A-Z])', ' $1')" />
		<xsl:variable name="words">
			<xsl:for-each select="tokenize($withSpaces,' ')">
				<xsl:copy-of select="lower-case(.)" />
			</xsl:for-each>
		</xsl:variable>
		<xsl:copy-of select="string-join($words,' ')" />
	</xsl:function>

	<xsl:function name="zenta:lastDotted">
		<xsl:param name="qualified" />
		<xsl:choose>
			<xsl:when test="count($qualified)>1">
				<xsl:variable name="lasts">
					<xsl:for-each select="$qualified">
						<xsl:copy-of select="zenta:lastDotted(.)" />
					</xsl:for-each>
				</xsl:variable>
				<xsl:copy-of select="string-join($lasts, ',')" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy-of select="tokenize($qualified, '\.')[last()]" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:function>

	<xsl:template match="/">
		<folder name="generated structure"
			id="folder_generated_structure">
			<xsl:apply-templates select="*" />
		</folder>
	</xsl:template>
	<xsl:template match="package">
		<xsl:variable name="lastpart"
			select="zenta:lastDotted(@name)" />
		<folder name="{@name}" id="package_folder_{@name}">
			<element xsi:type="zenta:BasicObject" name="{$lastpart}"
				id="package_{@name}" ancestor="package" />
			<xsl:apply-templates
				select="package|class|interface" />
		</folder>
	</xsl:template>

	<xsl:template match="class|interface">
		<xsl:variable name="package" select="../@name" />
		<xsl:variable name="id" select="@qualified" />
		<xsl:variable name="type">
			<xsl:choose>
				<xsl:when test="'interface' = name()">
					service
				</xsl:when>
				<xsl:when
					test="./annotation/@name='Getter' or ./annotation/@name='Setter' or ./annotation/@name='Entity'">
					object
				</xsl:when>
				<xsl:otherwise>
					class
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<element xsi:type="zenta:BasicObject" name="{@name}"
			id="{$id}" ancestor="{$type}" />
		<element xsi:type="zenta:BasicRelationship"
			id="packageof_{$id}" ancestor="application_component_contains"
			source="package_{$package}" target="{$id}" />
		<xsl:for-each select="field">
			<xsl:choose>
				<xsl:when test="annotation[@name='Autowired']">
					<xsl:variable name="dependent"
						select="type/@qualified" />
					<element xsi:type="zenta:BasicRelationship"
						id="service_dependency_{$id}_{$dependent}"
						ancestor="service_depends_service" source="{$id}"
						target="{$dependent}" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="type"
						mode="object">
						<xsl:with-param name="parentId" select="$id" />
						<xsl:with-param name="prefix"
							select="concat('field_', @name,'_')" />
						<xsl:with-param name="relationName" select="@name" />
						<xsl:with-param name="relationType"
							select="'application_component_contains'" />
					</xsl:apply-templates>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
		<xsl:for-each select="interface|annotation|class">
			<element xsi:type="zenta:BasicRelationship"
				id="{name()}_of_{@qualified}_{$id}"
				ancestor="appcomponent_inherits_appcomponent" source="{$id}"
				target="{@qualified}" />
		</xsl:for-each>
		<xsl:if test="$type='service'">
			<xsl:apply-templates select="method" />
		</xsl:if>
	</xsl:template>


	<xsl:template match="method">
		<xsl:variable name="package" select="../../@name" />
		<xsl:variable name="interface" select="../@name" />
		<xsl:variable name="id" select="@qualified" />
		<xsl:variable name="name"
			select="zenta:camelToText(@name)" />
		<xsl:variable name="serviceId"
			select="concat($package, '.', $interface)" />
		<element xsi:type="zenta:BasicObject" name="{$name}"
			id="{$id}" ancestor="service_method" />
		<element xsi:type="zenta:BasicRelationship"
			id="serviceof_{$id}" ancestor="application_component_contains"
			source="{$serviceId}" target="{$id}" />
		<xsl:apply-templates select="return" mode="object">
			<xsl:with-param name="parentId" select="$id" />
			<xsl:with-param name="prefix" select="'return_of'" />
			<xsl:with-param name="relationType" select="'outputs'" />
		</xsl:apply-templates>
		<xsl:for-each select="parameter">
			<xsl:apply-templates select="type" mode="object">
				<xsl:with-param name="parentId" select="$id" />
				<xsl:with-param name="prefix"
					select="concat('parameter_', @name,'_')" />
				<xsl:with-param name="relationName" select="@name" />
				<xsl:with-param name="relationType"
					select="'parameter'" />
			</xsl:apply-templates>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="*" mode="object">
		<xsl:param name="parentId" />
		<xsl:param name="prefix" />
		<xsl:param name="relationName" select="''" />
		<xsl:param name="relationType" />
		<xsl:param name="alwaysObject" select="false()" />
		<xsl:param name="ancestorType" select="'object'" />
		<xsl:choose>
			<xsl:when test="generic or $alwaysObject">
				<xsl:variable name="objectId"
					select="concat($prefix, '_object_', $parentId)" />
				<xsl:variable name="objName"
					select="
			if(generic)
				then
					concat(zenta:lastDotted(@qualified), '&lt;', zenta:lastDotted(generic/@qualified), '&gt;')
				else
					zenta:lastDotted(@qualified)
				" />
				<element xsi:type="zenta:BasicObject" name="{$objName}"
					id="{$objectId}" ancestor="{$ancestorType}" />
				<element xsi:type="zenta:BasicRelationship"
					id="belonging_{$objectId}" ancestor="{$relationType}"
					name="{$relationName}" source="{$parentId}" target="{$objectId}" />
				<element xsi:type="zenta:BasicRelationship"
					id="type_of_{$objectId}" ancestor="is_of_type" source="{$objectId}"
					target="{@qualified}" />
				<xsl:for-each select="generic">
					<xsl:variable name="relationName"
						select="
				if (last()=1)
				then
					''
				else if (position()=1)
					then
						'key'
					else
						'value'
			" />
					<element xsi:type="zenta:BasicRelationship"
						name="{$relationName}" id="generic_of_{$objectId}_{@qualified}"
						ancestor="has_generic" source="{$objectId}" target="{@qualified}" />
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<element xsi:type="zenta:BasicRelationship"
					id="{$prefix}_{$parentId}" name="{$relationName}"
					ancestor="{$relationType}" source="{$parentId}"
					target="{@qualified}" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
