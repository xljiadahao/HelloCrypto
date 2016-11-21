package com.hellocrypto.entity;

import java.sql.Timestamp;
import java.util.Arrays;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author leixu2
 */
@Entity
@Table(name="certificate")
public class Certificate {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;
    @Basic
    @Column(name="NAME")
    private String name;
    @Basic
    @Column(name="PUB_KEY_FINGERPRINT")
    private String pubKeyFingerprint;
    @Basic
    @Column(name = "CERTIFICATE_BINARY")
    @Lob
    private byte[] certificateBinary;
    @Basic
    @Column(name="TIMESTAMP")
    private Timestamp timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPubKeyFingerprint() {
        return pubKeyFingerprint;
    }

    public void setPubKeyFingerprint(String pubKeyFingerprint) {
        this.pubKeyFingerprint = pubKeyFingerprint;
    }

    public byte[] getCertificateBinary() {
        return certificateBinary;
    }

    public void setCertificateBinary(byte[] certificateBinary) {
        this.certificateBinary = certificateBinary;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Certificate [id=" + id + ", name=" + name + ", pubKeyFingerprint=" + pubKeyFingerprint
                + ", certificateBinary=" + Arrays.toString(certificateBinary) + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(certificateBinary);
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((pubKeyFingerprint == null) ? 0 : pubKeyFingerprint.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Certificate other = (Certificate) obj;
        if (!Arrays.equals(certificateBinary, other.certificateBinary)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (pubKeyFingerprint == null) {
            if (other.pubKeyFingerprint != null) {
                return false;
            }
        } else if (!pubKeyFingerprint.equals(other.pubKeyFingerprint)) {
            return false;
        }
        return true;
    }

}
