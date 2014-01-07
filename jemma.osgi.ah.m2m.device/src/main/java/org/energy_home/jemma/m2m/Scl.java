/**
 * This file is part of JEMMA - http://jemma.energy-home.org
 * (C) Copyright 2013 Telecom Italia (http://www.telecomitalia.it)
 *
 * JEMMA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License (LGPL) version 3
 * or later as published by the Free Software Foundation, which accompanies
 * this distribution and is available at http://www.gnu.org/licenses/lgpl.html
 *
 * JEMMA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License (LGPL) for more details.
 *
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.19 at 12:17:52 PM CET 
//


package org.energy_home.jemma.m2m;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{http://schemas.telecomitalia.it/m2m}CommonResourcesGroup"/>
 *         &lt;element name="SclBaseId" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="OnLineStatus" type="{http://schemas.telecomitalia.it/m2m}SclStatusEnumeration" minOccurs="0"/>
 *         &lt;element name="ExpirationTime" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "accessRightId",
    "creationTime",
    "lastModifiedTime",
    "sclBaseId",
    "onLineStatus",
    "expirationTime"
})
@XmlRootElement(name = "Scl")
public class Scl
    extends M2MXmlObject
{

    @XmlElement(name = "Id")
    @XmlSchemaType(name = "anyURI")
    protected String id;
    @XmlElement(name = "AccessRightId")
    @XmlSchemaType(name = "anyURI")
    protected String accessRightId;
    @XmlElement(name = "CreationTime")
    protected Long creationTime;
    @XmlElement(name = "LastModifiedTime")
    protected Long lastModifiedTime;
    @XmlElement(name = "SclBaseId")
    @XmlSchemaType(name = "anyURI")
    protected String sclBaseId;
    @XmlElement(name = "OnLineStatus")
    protected SclStatusEnumeration onLineStatus;
    @XmlElement(name = "ExpirationTime")
    protected Long expirationTime;

    /**
     * 
     * The id property is a unique identifier of the resource related to the containing 
     * resource identifier specified by the the context of the HTTP Request or by a parent 
     * element.
     *                             
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the accessRightId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessRightId() {
        return accessRightId;
    }

    /**
     * Sets the value of the accessRightId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessRightId(String value) {
        this.accessRightId = value;
    }

    /**
     *                           
     *     The CreationTime property specifies the time Time of creation of the resource as UTC 
     *     milliseconds from the Epoch (January 1, 1970 00:00:00.000 GMT)  
     *                             
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCreationTime() {
        return creationTime;
    }

    /**
     * Sets the value of the creationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCreationTime(Long value) {
        this.creationTime = value;
    }

    /**
     * Gets the value of the lastModifiedTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    /**
     * Sets the value of the lastModifiedTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLastModifiedTime(Long value) {
        this.lastModifiedTime = value;
    }

    /**
     * Gets the value of the sclBaseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSclBaseId() {
        return sclBaseId;
    }

    /**
     * Sets the value of the sclBaseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSclBaseId(String value) {
        this.sclBaseId = value;
    }

    /**
     * Gets the value of the onLineStatus property.
     * 
     * @return
     *     possible object is
     *     {@link SclStatusEnumeration }
     *     
     */
    public SclStatusEnumeration getOnLineStatus() {
        return onLineStatus;
    }

    /**
     * Sets the value of the onLineStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link SclStatusEnumeration }
     *     
     */
    public void setOnLineStatus(SclStatusEnumeration value) {
        this.onLineStatus = value;
    }

    /**
     * Gets the value of the expirationTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getExpirationTime() {
        return expirationTime;
    }

    /**
     * Sets the value of the expirationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setExpirationTime(Long value) {
        this.expirationTime = value;
    }

}
