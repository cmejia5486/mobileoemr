/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package com.openmrs.android_sdk.library.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update
import com.openmrs.android_sdk.library.models.Person
import com.openmrs.android_sdk.library.models.Provider
import com.openmrs.android_sdk.library.models.typeConverters.PersonConverter
import io.reactivex.Single

/**
 * The interface Provider room dao.
 */
@Dao
interface ProviderRoomDAO {
    /**
     * Add provider long.
     *
     * @param provider the provider
     * @return the long
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addProvider(provider: Provider): Long

    /**
     * Insert all orders.
     *
     * @param order the order
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllOrders(order: List<Provider>)

    /**
     * Delete provider.
     *
     * @param provider the provider
     */
    @Delete
    fun deleteProvider(provider: Provider)

    /**
     * Delete by uuid.
     *
     * @param uuid the uuid
     */
    @Query("DELETE FROM provider_table WHERE uuid = :uuid")
    fun deleteByUuid(uuid: String)

    /**
     * Update provider.
     *
     * @param provider the provider
     */
    @Update
    fun updateProvider(provider: Provider)

    /**
     * since the current implementation of the provider update operation
     * only changes display and person field in the provider entity
     *
     * @param display    the display
     * @param id         the id
     * @param person     the person
     * @param uuid       the uuid
     * @param identifier the identifier
     */
    @TypeConverters(PersonConverter::class)
    @Query("UPDATE provider_table SET person= :person, _id= :id, display=:display, identifier=:identifier WHERE uuid = :uuid")
    fun updateProviderByUuid(
        display: String,
        id: Long,
        person: Person,
        uuid: String,
        identifier: String
    )

    /**
     * Update provider uuid by id.
     *
     * @param id   the id
     * @param uuid the uuid
     */
    @Query("UPDATE provider_table SET uuid=:uuid WHERE _id=:id")
    fun updateProviderUuidById(id: Long, uuid: String)

    /**
     * Gets provider list.
     *
     * @return the provider list
     */
    @Query("SELECT * FROM provider_table")
    fun getProviderList(): Single<List<Provider>>

    /**
     * Find provider by id single.
     *
     * @param id the id
     * @return the single
     */
    @Query("SELECT * FROM provider_table WHERE _id = :id")
    fun findProviderByID(id: Long): Single<Provider>

    /**
     * Find provider by uuid single.
     *
     * @param uuid the uuid
     * @return the single
     */
    @Query("SELECT * FROM provider_table WHERE uuid = :uuid")
    fun findProviderByUUID(uuid: String): Single<Provider>

    /**
     * Gets current uui ds.
     *
     * @return the current uui ds
     */
    @Query("SELECT uuid FROM provider_table")
    fun getCurrentUUIDs(): Single<List<String>>

    /**
     * Delete all.
     */
    @Query("DELETE FROM provider_table")
    fun deleteAll()
}