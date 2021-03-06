package com.schooltriangle.pojos;

import java.io.Serializable;

public class ServiceConfiguration implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4112760535933560096L;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getServiceTypeId() {
        return ServiceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        ServiceTypeId = serviceTypeId;
    }

    public int getDeliveryModeId() {
        return DeliveryModeId;
    }

    public void setDeliveryModeId(int deliveryModeId) {
        DeliveryModeId = deliveryModeId;
    }

    public ServiceType getServiceType() {
        return ServiceType;
    }

    public void setServiceType(ServiceType serviceType) {
        ServiceType = serviceType;
    }

    public DeliveryMode getDeliveryMode() {
        return DeliveryMode;
    }

    public void setDeliveryMode(DeliveryMode deliveryMode) {
        DeliveryMode = deliveryMode;
    }

    public double getFees() {
        return Fees;
    }

    public void setFees(double fees) {
        Fees = fees;
    }

    public double getCommission() {
        return Commission;
    }

    public void setCommission(double commission) {
        Commission = commission;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public boolean isIsPaid() {
        return IsPaid;
    }

    public void setIsPaid(boolean isPaid) {
        IsPaid = isPaid;
    }

    public boolean isIsHandledBySupport() {
        return IsHandledBySupport;
    }

    public void setIsHandledBySupport(boolean isHandledBySupport) {
        IsHandledBySupport = isHandledBySupport;
    }

    public boolean isIsProviderNotified() {
        return IsProviderNotified;
    }

    public void setIsProviderNotified(boolean isProviderNotified) {
        IsProviderNotified = isProviderNotified;
    }

    public boolean isIsAppointmentApplicable() {
        return IsAppointmentApplicable;
    }

    public void setIsAppointmentApplicable(boolean isAppointmentApplicable) {
        IsAppointmentApplicable = isAppointmentApplicable;
    }

    public boolean isIsAppointmentBasedOnly() {
        return IsAppointmentBasedOnly;
    }

    public void setIsAppointmentBasedOnly(boolean isAppointmentBasedOnly) {
        IsAppointmentBasedOnly = isAppointmentBasedOnly;
    }

    public boolean isIsConsumerAddressRequired() {
        return IsConsumerAddressRequired;
    }

    public void setIsConsumerAddressRequired(boolean isConsumerAddressRequired) {
        IsConsumerAddressRequired = isConsumerAddressRequired;
    }

    public boolean isIsProviderAddressRequired() {
        return IsProviderAddressRequired;
    }

    public void setIsProviderAddressRequired(boolean isProviderAddressRequired) {
        IsProviderAddressRequired = isProviderAddressRequired;
    }

    public boolean isIsConsumerEmailRequired() {
        return IsConsumerEmailRequired;
    }

    public void setIsConsumerEmailRequired(boolean isConsumerEmailRequired) {
        IsConsumerEmailRequired = isConsumerEmailRequired;
    }

    public boolean isIsProviderEmailRequired() {
        return IsProviderEmailRequired;
    }

    public void setIsProviderEmailRequired(boolean isProviderEmailRequired) {
        IsProviderEmailRequired = isProviderEmailRequired;
    }

    public boolean isIsConsumerPhoneRequired() {
        return IsConsumerPhoneRequired;
    }

    public void setIsConsumerPhoneRequired(boolean isConsumerPhoneRequired) {
        IsConsumerPhoneRequired = isConsumerPhoneRequired;
    }

    public boolean isIsProviderPhoneRequired() {
        return IsProviderPhoneRequired;
    }

    public void setIsProviderPhoneRequired(boolean isProviderPhoneRequired) {
        IsProviderPhoneRequired = isProviderPhoneRequired;
    }

    public boolean isIsOnlinePaymentRequired() {
        return IsOnlinePaymentRequired;
    }

    public void setIsOnlinePaymentRequired(boolean isOnlinePaymentRequired) {
        IsOnlinePaymentRequired = isOnlinePaymentRequired;
    }

    public boolean isIsAttachDocumentAllowed() {
        return IsAttachDocumentAllowed;
    }

    public void setIsAttachDocumentAllowed(boolean isAttachDocumentAllowed) {
        IsAttachDocumentAllowed = isAttachDocumentAllowed;
    }

    public boolean isIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean isActive) {
        IsActive = isActive;
    }

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedOnUTC() {
        return CreatedOnUTC;
    }

    public void setCreatedOnUTC(String createdOnUTC) {
        CreatedOnUTC = createdOnUTC;
    }

    public int getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getUpdatedOnUTC() {
        return UpdatedOnUTC;
    }

    public void setUpdatedOnUTC(String updatedOnUTC) {
        UpdatedOnUTC = updatedOnUTC;
    }

    public boolean isIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        IsDeleted = isDeleted;
    }

    public String getUniquePublicID() {
        return UniquePublicID;
    }

    public void setUniquePublicID(String uniquePublicID) {
        UniquePublicID = uniquePublicID;
    }

    private int Id;
    private String Name;
    private String Description;
    private String UniquePublicID;
    private int ServiceTypeId;
    private int DeliveryModeId;
    private ServiceType ServiceType;
    private DeliveryMode DeliveryMode;
    private double Fees;
    private double Commission;
    private double Discount;
    private boolean IsPaid;
    private boolean IsHandledBySupport;
    private boolean IsProviderNotified;
    private boolean IsAppointmentApplicable;
    private boolean IsAppointmentBasedOnly;
    private boolean IsConsumerAddressRequired;
    private boolean IsProviderAddressRequired;
    private boolean IsConsumerEmailRequired;
    private boolean IsProviderEmailRequired;
    private boolean IsConsumerPhoneRequired;
    private boolean IsProviderPhoneRequired;
    private boolean IsOnlinePaymentRequired;
    private boolean IsAttachDocumentAllowed;
    private boolean IsActive;
    private int CreatedBy;
    private String CreatedOnUTC;
    private int UpdatedBy;
    private String UpdatedOnUTC;
    private boolean IsDeleted;
}
