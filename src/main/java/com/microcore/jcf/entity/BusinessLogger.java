package com.microcore.jcf.entity;


import com.microcore.jcf.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "TB_LOGGER")
public class BusinessLogger extends BaseEntity
{
    @Id
    @Column(name = "LOG_ID")
    private String logId;

    @Column(name = "OPERATE_TYPE")
    private Integer operateType;

    @Column(name = "LOG_TYPE")
    private Integer logType;

    @Column(name = "LOG_LEVEL")
    private Integer logLevel;

    @Column(name = "LOG_TITLE")
    private String logTitle;

    @Column(name = "LOG_DETAIL")
    private String logDetail;

    @Column(name = "LOG_RESULT")
    private Integer logResult;

    @Column(name = "LOG_SOURCE")
    private String logSource;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    /**
     * @return OPERATE_TYPE
     */
    public Integer getOperateType()
    {
        return operateType;
    }

    /**
     * @param operateType
     */
    public void setOperateType(Integer operateType)
    {
        this.operateType = operateType;
    }

    /**
     * @return LOG_TYPE
     */
    public Integer getLogType()
    {
        return logType;
    }

    /**
     * @param logType
     */
    public void setLogType(Integer logType)
    {
        this.logType = logType;
    }

    /**
     * @return LOG_LEVEL
     */
    public Integer getLogLevel()
    {
        return logLevel;
    }

    /**
     * @param logLevel
     */
    public void setLogLevel(Integer logLevel)
    {
        this.logLevel = logLevel;
    }

    /**
     * @return LOG_TITLE
     */
    public String getLogTitle()
    {
        return logTitle;
    }

    /**
     * @param logTitle
     */
    public void setLogTitle(String logTitle)
    {
        this.logTitle = logTitle;
    }

    /**
     * @return LOG_DETAIL
     */
    public String getLogDetail()
    {
        return logDetail;
    }

    /**
     * @param logDetail
     */
    public void setLogDetail(String logDetail)
    {
        this.logDetail = logDetail;
    }

    /**
     * @return LOG_RESULT
     */
    public Integer getLogResult()
    {
        return logResult;
    }

    /**
     * @param logResult
     */
    public void setLogResult(Integer logResult)
    {
        this.logResult = logResult;
    }

    /**
     * @return LOG_SOURCE
     */
    public String getLogSource()
    {
        return logSource;
    }

    /**
     * @param logSource
     */
    public void setLogSource(String logSource)
    {
        this.logSource = logSource;
    }

    public String getLogId()
    {
        return logId;
    }

    public void setLogId(String logId)
    {
        this.logId = logId;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }

        BusinessLogger that = (BusinessLogger) o;

        if (logId != null ? !logId.equals(that.logId) : that.logId != null)
        {
            return false;
        }
        if (operateType != null ? !operateType.equals(that.operateType) : that.operateType != null)
        {
            return false;
        }
        if (logType != null ? !logType.equals(that.logType) : that.logType != null)
        {
            return false;
        }
        if (logLevel != null ? !logLevel.equals(that.logLevel) : that.logLevel != null)
        {
            return false;
        }
        if (logTitle != null ? !logTitle.equals(that.logTitle) : that.logTitle != null)
        {
            return false;
        }
        if (logDetail != null ? !logDetail.equals(that.logDetail) : that.logDetail != null)
        {
            return false;
        }
        if (logResult != null ? !logResult.equals(that.logResult) : that.logResult != null)
        {
            return false;
        }
        return logSource != null ? logSource.equals(that.logSource) : that.logSource == null;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (logId != null ? logId.hashCode() : 0);
        result = 31 * result + (operateType != null ? operateType.hashCode() : 0);
        result = 31 * result + (logType != null ? logType.hashCode() : 0);
        result = 31 * result + (logLevel != null ? logLevel.hashCode() : 0);
        result = 31 * result + (logTitle != null ? logTitle.hashCode() : 0);
        result = 31 * result + (logDetail != null ? logDetail.hashCode() : 0);
        result = 31 * result + (logResult != null ? logResult.hashCode() : 0);
        result = 31 * result + (logSource != null ? logSource.hashCode() : 0);
        return result;
    }

    public static class Field
    {
        public static final String LOG_ID = "logId";
        public static final String OPERATE_TYPE = "operateType";
        public static final String LOG_TYPE = "logType";
        public static final String LOG_LEVEL = "logLevel";
        public static final String LOG_TITLE = "logTitle";
        public static final String LOG_DETAIL = "logDetail";
        public static final String LOG_RESULT = "logResult";
        public static final String LOG_SOURCE = "logSource";
    }
}