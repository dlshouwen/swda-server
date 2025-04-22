Sub 处理初始化脚本()

    ' 去掉网格线
    For i = 1 To Sheets.Count - 2
        Set Sheet = Sheets(i)
        Sheet.Select
        ActiveWindow.DisplayGridlines = False
    Next
    
    ' 设置全局字体样式
    For i = 1 To Sheets.Count
        Set Sheet = Sheets(i)
        With Sheet.Cells.Font
            .Name = "微软雅黑"
            .Size = 9
            .Color = RGB(66, 66, 66)
        End With
        With Sheet.Cells.Font
            .Name = "Calibri"
            .Size = 9
            .Color = RGB(66, 66, 66)
        End With
    Next

    ' 设置边框样式，行高及自适应列宽
    For i = 1 To Sheets.Count
        
        Set Sheet = Sheets(i)
        RowCount = Sheet.UsedRange.Rows.Count
        ColumnCount = Sheet.UsedRange.Columns.Count
        
        Position = ""
        
        If ColumnCount > 26 Then
            Position = "A"
        End If
        
        Position = Position + Chr(ColumnCount Mod 26 + 64)
       
        Set Datas = Sheet.Range("A1:" + Position + CStr(RowCount))
        Datas.Borders(xlDiagonalDown).LineStyle = xlNone
        Datas.Borders(xlDiagonalUp).LineStyle = xlNone
        
        With Datas.Borders(xlEdgeLeft)
            .LineStyle = xlContinuous
            .ColorIndex = 0
            .TintAndShade = 0
            .Weight = xlThin
        End With
        With Datas.Borders(xlEdgeTop)
            .LineStyle = xlContinuous
            .ColorIndex = 0
            .TintAndShade = 0
            .Weight = xlThin
        End With
        With Datas.Borders(xlEdgeBottom)
            .LineStyle = xlContinuous
            .ColorIndex = 0
            .TintAndShade = 0
            .Weight = xlThin
        End With
        With Datas.Borders(xlEdgeRight)
            .LineStyle = xlContinuous
            .ColorIndex = 0
            .TintAndShade = 0
            .Weight = xlThin
        End With
        With Datas.Borders(xlInsideVertical)
            .LineStyle = xlContinuous
            .ColorIndex = 0
            .TintAndShade = 0
            .Weight = xlThin
        End With
        With Datas.Borders(xlInsideHorizontal)
            .LineStyle = xlContinuous
            .ColorIndex = 0
            .TintAndShade = 0
            .Weight = xlThin
        End With
        
        Datas.Columns.AutoFit
        Datas.RowHeight = 18
        
    Next
    
    ' 设置表头变色
    For i = 1 To Sheets.Count
        
        Set Sheet = Sheets(i)
        RowCount = Sheet.UsedRange.Rows.Count
        ColumnCount = Sheet.UsedRange.Columns.Count
        
        Position = ""
        
        If ColumnCount > 26 Then
            Position = "A"
        End If
        
        Position = Position + Chr(ColumnCount Mod 26 + 64)
       
        Set Datas = Sheet.Range("A1:" + Position + "1")
        
        With Datas.Font
            .ThemeColor = xlThemeColorDark1
            .TintAndShade = 0
        End With
        With Datas.Interior
            .Pattern = xlSolid
            .PatternColorIndex = xlAutomatic
            .Color = 15773696
            .TintAndShade = 0
            .PatternTintAndShade = 0
        End With
        Datas.Font.Bold = True
        With Datas
            .HorizontalAlignment = xlCenter
            .VerticalAlignment = xlCenter
            .WrapText = False
            .Orientation = 0
            .AddIndent = False
            .IndentLevel = 0
            .ShrinkToFit = False
            .ReadingOrder = xlContext
            .MergeCells = False
        End With
        
    Next

    ' 设置 表 跳转
    For i = 1 To Sheets.Count
        
        Set Sheet = Sheets(i)
        RowCount = Sheet.UsedRange.Rows.Count
        ColumnCount = Sheet.UsedRange.Columns.Count
        
        If Sheet.Name = "tables" Then
            
            For j = 2 To RowCount
                Sheet.Hyperlinks.Add Anchor:=Sheet.Cells(j, 2), Address:="", SubAddress:= _
                "'" + Sheets(j).Name + "'!A1"
                Sheet.Cells(j, 2).Font.Size = 9
            Next
           
        End If
        
        If Sheet.Name <> "tables" Then
            
            Sheet.Hyperlinks.Add Anchor:=Sheet.Cells(1, 1), Address:="", SubAddress:= _
            "'tables'!A1"
            Sheet.Cells(1, 1).Font.Size = 9
           
        End If
        
    Next

    ' 处理功能
    For i = 1 To Sheets.Count
        
        Set Sheet = Sheets(i)
        RowCount = Sheet.UsedRange.Rows.Count
        ColumnCount = Sheet.UsedRange.Columns.Count
        
        level1 = 1
        level2 = 1
        level3 = 1
        level4 = 1
        
        If Sheet.Name = "bms_menu" Then
        
            For j = 2 To RowCount
                s = ""
                For k = 1 To CInt(Sheet.Cells(j, 7)) - 1
                    s = s + "　　"
                Next
                Sheet.Cells(j, 5) = s + Sheet.Cells(j, 6)
            Next
            
            system = ""
            
            For j = 2 To RowCount
            
            	If Sheet.Cells(j, 3) <> system Then
            		system = Sheet.Cells(j, 3)
            		level1 = 1
            		level2 = 1
            		level3 = 1
            		level4 = 1
            	End If
            
                If Sheet.Cells(j, 7) = "1" Then
                    Sheet.Cells(j, 1) = Sheet.Cells(j, 3) + IIf(level1 < 10, ("0" + CStr(level1)), CStr(level1))
                    Sheet.Cells(j, 2) = Sheet.Cells(j, 3)
                    level1 = level1 + 1
                    level2 = 1
                    level3 = 1
                    level4 = 1
                End If
                
                If Sheet.Cells(j, 7) = "2" Then
                    Sheet.Cells(j, 1) = Sheet.Cells(j, 3) + IIf(level1 < 11, ("0" + CStr(level1 - 1)), CStr(level1 - 1)) + IIf(level2 < 10, ("0" + CStr(level2)), CStr(level2))
                    Sheet.Cells(j, 2) = Sheet.Cells(j, 3) + IIf(level1 < 11, ("0" + CStr(level1 - 1)), CStr(level1 - 1))
                    level2 = level2 + 1
                    level3 = 1
                    level4 = 1
                End If
                
                If Sheet.Cells(j, 7) = "3" Then
                    Sheet.Cells(j, 1) = Sheet.Cells(j, 3) + IIf(level1 < 11, ("0" + CStr(level1 - 1)), CStr(level1 - 1)) + IIf(level2 < 11, ("0" + CStr(level2 - 1)), CStr(level2 - 1)) + IIf(level3 < 10, ("0" + CStr(level3)), CStr(level3))
                    Sheet.Cells(j, 2) = Sheet.Cells(j, 3) + IIf(level1 < 11, ("0" + CStr(level1 - 1)), CStr(level1 - 1)) + IIf(level2 < 11, ("0" + CStr(level2 - 1)), CStr(level2 - 1))
                    level3 = level3 + 1
                    level4 = 1
                End If
                
                If Sheet.Cells(j, 7) = "4" Then
                    Sheet.Cells(j, 1) = Sheet.Cells(j, 3) + IIf(level1 < 11, ("0" + CStr(level1 - 1)), CStr(level1 - 1)) + IIf(level2 < 11, ("0" + CStr(level2 - 1)), CStr(level2 - 1)) + IIf(level3 < 11, ("0" + CStr(level3 - 1)), CStr(level3 - 1)) + IIf(level4 < 10, ("0" + CStr(level4)), CStr(level4))
                    Sheet.Cells(j, 2) = Sheet.Cells(j, 3) + IIf(level1 < 11, ("0" + CStr(level1 - 1)), CStr(level1 - 1)) + IIf(level2 < 11, ("0" + CStr(level2 - 1)), CStr(level2 - 1)) + IIf(level3 < 11, ("0" + CStr(level3 - 1)), CStr(level3 - 1))
                    level4 = level4 + 1
                End If
            
            Next
            
        End If
        
    Next

End Sub

