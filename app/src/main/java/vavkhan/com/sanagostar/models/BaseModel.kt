package vavkhan.com.sanagostar.models

import org.json.JSONObject
import vavkhan.com.sanagostar.utils.GsonHelper

/**
 * Parent Model Fot Child Model
 */
abstract class BaseModel {
    /**
     * Convert Model to JsonString
     * @return Json String of this model
     */
    fun toJsonString(): String? {
        return GsonHelper.convertModelToString(this)
    }

    /**
     * Convert Model To JSONObject
     * @return Json object of this model
     */
    fun toJson(): JSONObject? {
        return GsonHelper.convertToJson(this)
    }
}