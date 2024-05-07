Option Explicit

Dim mdl
Dim opt

Set mdl = ActiveModel
Set opt = mdl.GetModelOptions()
opt.EnableNameCodeTranslation = true
opt.save
opt.UpdateModelOptions
ProcessFolder 

Private sub ProcessFolder
	Dim tbl, col, inx, inxc
	for each tbl in mdl.Tables
		if InStr(tbl.code, "pr_") > 0 then
			for each inx in tbl.indexes
			  inx.delete
			next
		end if
	next
   for each tbl in mdl.Tables
      if InStr(tbl.code, "pr_") > 0 then
   		for each col in tbl.columns
            if col.primary = false and col.code <> "remark" and col.code <> "creator" and col.code <> "create_time" and col.code <> "editor" and col.code <> "edit_time" and col.code <> "deleter" and col.code <> "delete_time" then
               set inx = tbl.Indexes.CreateNew
               inx.name = "i_"+col.code
               inx.code = "i_"+col.code
               set inxc = inx.indexColumns.createNew
               inxc.column = col
            end if
         next
      end if
	next
end sub