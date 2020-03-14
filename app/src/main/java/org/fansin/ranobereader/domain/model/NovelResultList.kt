package org.fansin.ranobereader.domain.model

import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
class
NovelResultList(val items: List<Novel>)
