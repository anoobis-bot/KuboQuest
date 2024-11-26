package com.mobdeve.harvesters.kuboquest;

public class FireStoreReferences {
    public final static String USER_COLLECTION = "Users";
    public final static String TASK_COLLECTION = "Tasks";
    public final static String PLANT_COLLECTION = "Plants";

    //user fields
    public final static String EMAIL_FIELD = "email";
    public final static String USERNAME_FIELD = "username";
    public final static String TOTALENERGYSAVED_FIELD = "totalEnergySaved";
    public final static String ACTIVEPLANT_FIELD = "activePlant";
    public final static String WATERLEVEL_FIELD = "waterLevel";

    //task fields
    public final static String TASKNAME_FIELD = "taskName";
    public final static String TASKDESC_FIELD = "taskDesc";
    public final static String TASKSTARTDATE_FIELD = "taskStartDate";
    public final static String TASKFREQUENCY_FIELD = "taskFrequency";
    public final static String TASKDIFFICULTY_FIELD = "taskDifficulty";
    public final static String TASKISDONE_FIELD = "taskIsDone";

    //plant fields
    public final static String PLANTNAME_FIELD = "plantName";
    public final static String CURRENTXP_FIELD = "currentXP";
    public final static String ISLOCKED_FIELD = "isLocked";
}
