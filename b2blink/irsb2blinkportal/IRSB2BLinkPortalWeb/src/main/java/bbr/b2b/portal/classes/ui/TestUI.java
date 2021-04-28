package bbr.b2b.portal.classes.ui;

import org.fusesource.jansi.Ansi.Color;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisTitle;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.ColorAxis;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.DataLabels;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsBar;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsColumnrange;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.StackLabels;
import com.vaadin.addon.charts.model.Stacking;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;

import cl.bbr.core.components.basics.BbrUI;

@SuppressWarnings("serial")
@Theme("irsb2blinkportalweb")
public class TestUI extends BbrUI
{
	@Override
	protected void init(VaadinRequest request)
	{

		// Resource res1 =
		// BbrThemeResourcesUtils.getInstance().getResource(this,CoreConstants.PATH_BASE_IMAGES_BUTTONS
		// + "icon_SearchPrimary.png");
		// Resource res2 =
		// BbrThemeResourcesUtils.getInstance().getResource(this,CoreConstants.PATH_BASE_IMAGES_BUTTONS
		// + "icon_SearchPrimary.png");
		// Resource res3 =
		// BbrThemeResourcesUtils.getInstance().getResource(this,CoreConstants.PATH_BASE_IMAGES_BUTTONS
		// + "icon_SearchPrimary.png");
		// Resource res4 =
		// BbrThemeResourcesUtils.getInstance().getResource(this,CoreConstants.PATH_BASE_IMAGES_BUTTONS
		// + "icon_SearchPrimary.png");
		// Resource res5 =
		// BbrThemeResourcesUtils.getInstance().getResource(this,CoreConstants.PATH_BASE_IMAGES_BUTTONS
		// + "icon_SearchPrimary.png");
		//
		// GalleryItem<PersonDTO> p1 = new GalleryItem<>(res1);
		// GalleryItem<PersonDTO> p2 = new GalleryItem<>(res2);
		// GalleryItem<PersonDTO> p3 = new GalleryItem<>(res3);
		// GalleryItem<PersonDTO> p4 = new GalleryItem<>(res4);
		// GalleryItem<PersonDTO> p5 = new GalleryItem<>(res5);
		//
		// BbrComponentGallery<PersonDTO> gallery = new
		// BbrComponentGallery<>(p1,p2,p3,p4,p5);
		// gallery.setSelectionMode(BbrSelectionMode.MULTI);
		// gallery.setSizeFull();
		// VerticalLayout mainLayout = new VerticalLayout();
		// mainLayout.addComponents(gallery);
		// mainLayout.setMargin(true);
		// mainLayout.setSizeFull();

		// Label lbl = new Label("gdgfdgdfgdgfgdfgggdgdgfdgdfgdgfgdfgggdgdgfdg
		// dfgdgfgdfgggdgdgfdgdfgdgfgdfggg dgdgfdgdfgdgfgdfgggd");
		/////////////////////////////HEATMAP////////////////////
		// lbl.setWidth("100px");
		// Chart chart = new Chart(ChartType.HEATMAP);
		// chart.setWidth("600px");
		// chart.setHeight("300px");
		// chart.addPointClickListener((e)->{System.out.println("point click"+
		// e.getAbsoluteX());});
		//
		// Configuration conf = chart.getConfiguration();
		// conf.setTitle("Heat Data");
		//
		// // Set colors for the extremes
		// conf.getColorAxis().setMinColor(SolidColor.AQUA);
		// conf.getColorAxis().setMaxColor(SolidColor.RED);
		//
		// // Set up border and data labels
		// PlotOptionsHeatmap plotOptions = new PlotOptionsHeatmap();
		// plotOptions.setBorderColor(SolidColor.WHITE);
		// plotOptions.setBorderWidth(5);
		// plotOptions.setDataLabels(new DataLabels(true));
		// conf.setPlotOptions(plotOptions);
		//
		// // Create some data
		// HeatSeries series = new HeatSeries();
		// series.addHeatPoint( 0, 0, 10.9); // Jan High
		// series.addHeatPoint( 0, 1, -51.5); // Jan Low
		// series.addHeatPoint( 1, 0, 11.8); // Feb High
		// series.addHeatPoint(11, 1, -47.0); // Dec Low
		// conf.addSeries(series);
		//
		// // Set the category labels on the X axis
		// XAxis xaxis = new XAxis();
		// xaxis.setTitle("Month");
		// xaxis.setCategories("Jan", "Feb", "Mar",
		// "Apr", "May", "Jun", "Jul", "Aug", "Sep",
		// "Oct", "Nov", "Dec");
		// conf.addxAxis(xaxis);
		//
		// // Set the category labels on the Y axis
		// YAxis yaxis = new YAxis();
		// yaxis.setTitle("");
		// yaxis.setCategories("High C", "Low C");
		// conf.addyAxis(yaxis);
		// Chart chart = new Chart(ChartType.COLUMN);
		// chart.setWidth("600px");
		// chart.setHeight("600px");
		//
		// Configuration conf = chart.getConfiguration();
		// conf.setTitle(new Title(""));
		// XAxis xAxis = new XAxis();
		// xAxis.setCategories(new String[] { "Falabella", "Paris", "Ripley",
		// "Jumbo", "Hites" });
		// conf.addxAxis(xAxis);
		//
		// YAxis yAxis = new YAxis();
		// yAxis.setMin(0);
		// yAxis.setTitle(new AxisTitle(""));
		//
		// StackLabels sLabels = new StackLabels(true);
		// yAxis.setStackLabels(sLabels);
		// conf.addyAxis(yAxis);
		//
		// Legend legend = new Legend();
		// legend.setAlign(HorizontalAlign.CENTER);
		// legend.setVerticalAlign(VerticalAlign.MIDDLE);
		// legend.setFloating(true);
		// legend.setMargin(30);
		//// legend.setX(-100);
		// legend.setY(-30);
		// conf.setLegend(legend);
		//
		// Tooltip tooltip = new Tooltip();
		// tooltip.setFormatter("function() { return '<b>'+ this.x +'</b><br/>"
		// + "'+this.series.name +': '+ this.y +'<br/>'+'Total: '+
		// this.point.stackTotal;}");
		// conf.setTooltip(tooltip);
		//
		// PlotOptionsColumn plotOptions = new PlotOptionsColumn();
		// plotOptions.setStacking(Stacking.NORMAL);
		// DataLabels labels = new DataLabels(true);
		// Style style=new Style();
		// style.setTextShadow("0 0 1px black");
		// labels.setStyle(style);
		// labels.setColor(new SolidColor("white"));
		// plotOptions.setDataLabels(labels);
		// conf.setPlotOptions(plotOptions);
		//
		// conf.addSeries(new ListSeries("John", new Number[] { 5, 3, 4, 7, 2
		// }));
		// conf.addSeries(new ListSeries("Jane", new Number[] { -2, 2, 3, 2, 1
		// }));
		// conf.addSeries(new ListSeries("Joe", new Number[] { 3, 4, 4, 2, 5
		// }));
		//// conf.reverseListSeries();
		// chart.drawChart(conf);
		
		Chart chart = new Chart(ChartType.COLUMN);

		Configuration conf = chart.getConfiguration();
		conf.setTitle(new Title(""));
		
		YAxis yAxis = new YAxis();
		yAxis.setMin(0);
		yAxis.setTitle(new AxisTitle(""));
		yAxis.setGridLineColor(new SolidColor("white"));
		//TOTALES
		StackLabels sLabels = new StackLabels(true);
		yAxis.setStackLabels(sLabels);
		//TOTALES
		yAxis.setLabels(new Labels(false));
		conf.addyAxis(yAxis);

		PlotOptionsColumn column = new PlotOptionsColumn();
		column.setStacking(Stacking.NORMAL);
		
		DataLabels labels = new DataLabels(true);
		Style style = new Style();
		style.setTextShadow("0 0 1px black");
		labels.setStyle(style);
		labels.setColor(SolidColor.WHITE);
		column.setDataLabels(labels);

		conf.setPlotOptions(column);

		XAxis xAxis = new XAxis();
		xAxis.setCategories("PARIS", "RIPLEY", "FALABELLA", "HITES", "LA POLAR");
		
		conf.addxAxis(xAxis);

		Tooltip tooltip = new Tooltip();
		tooltip.setFormatter("function() { return ''+ this.series.name +': '+ this.y +'';}");
		conf.setTooltip(tooltip);

		conf.setCredits(new Credits(false));
		
		ListSeries series3 = new ListSeries("Error", 5, 0.1, 4, 7, 2);
		PlotOptionsColumn series3Options = new PlotOptionsColumn();
		series3Options.setColor(SolidColor.GREY);
		series3Options.setLegendIndex(3);
		series3.setPlotOptions(series3Options);
		conf.addSeries(series3);
		
		ListSeries series2 = new ListSeries("En Tránsito", 2, 2, 1, 2, 1);
		PlotOptionsColumn series2Options = new PlotOptionsColumn();
		series2Options.setColor(SolidColor.ORANGE);
		series2Options.setLegendIndex(2);
		series2.setPlotOptions(series2Options);
		conf.addSeries(series2);
		
		ListSeries series1 = new ListSeries("Recibidos", 3, 4, 4, 2, 5);
		PlotOptionsColumn series1Options = new PlotOptionsColumn();
		series1Options.setColor(SolidColor.BLUE);
		series1Options.setLegendIndex(1);
		series1.setPlotOptions(series1Options);
		conf.addSeries(series1);
		
		chart.drawChart(conf);
		
		this.setContent(chart);
	}

}
