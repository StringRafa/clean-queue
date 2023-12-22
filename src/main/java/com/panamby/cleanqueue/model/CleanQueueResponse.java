package com.panamby.cleanqueue.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleanQueueResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<String> messagesSuccessfullyDeleted;
	private List<String> failuresWhenDeletingMessages;
	private String totalMessagesPurged;
}
