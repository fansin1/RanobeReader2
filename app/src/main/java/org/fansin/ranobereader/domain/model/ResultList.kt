package org.fansin.ranobereader.domain.model

import androidx.room.Entity

@Entity
class ResultList<T>(val items: List<T>)